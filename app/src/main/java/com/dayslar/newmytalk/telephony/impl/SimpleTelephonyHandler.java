package com.dayslar.newmytalk.telephony.impl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.entity.TelephonyState;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.dayslar.newmytalk.recorder.impl.SimpleMediaRecorder;
import com.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.dayslar.newmytalk.services.NotificationCall;
import com.dayslar.newmytalk.services.UnloadService;
import com.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.dayslar.newmytalk.ui.activity.MainActivity_;
import com.dayslar.newmytalk.utils.ActivityUtils;
import com.dayslar.newmytalk.utils.SettingUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class SimpleTelephonyHandler implements TelephonyHandler {

    private static SimpleTelephonyHandler instance;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    private RecordDao recordDao;
    private TelephonyStateDao stateDao;
    private Recorder recorder;
    private Context context;

    private SettingUtil settingUtil;

    public static SimpleTelephonyHandler getInstance(Context context){
        if (instance == null) {
            synchronized (SimpleTelephonyHandler.class) {
                if (instance == null)
                    instance = new SimpleTelephonyHandler(context);
            }
        }

        return instance;
    }

    private SimpleTelephonyHandler(Context context){
        recordDao = SqlRecordDao.getInstance(context);
        recorder = SimpleMediaRecorder.getInstance(context);
        stateDao = SqlTelephonyStateDao.getInstance(context);
        settingUtil = SettingUtil.getInstance(context);

        this.context = context;
    }

    @Override
    public void outgoingCall(Intent intent) {
        String callPhone = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER").replaceAll(" ", "");

        if (checkUSSD(callPhone)) return;

        recordDao.insert(
                baseRecord(callPhone)
                        .setIncoming(false)
        );
    }

    @Override
    public void runningCall(Intent intent) {
        settingUtil.loadSetting();

        Record record =  baseRecord(getPhone(intent));
        recordDao.insert(record);

        stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.RINGING));

        if(settingUtil.getSetting().isManagerActive())
            ActivityUtils.startActivityUseHandler(context, MainActivity_.class, 1000);
    }

    @Override
    public void offhookCall(Intent intent) {
        stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.RECORDING));

        Record record = recordDao.last();

        recordDao.update(record
                .setFileName(record.getCallPhone() + "_" + sdf.format(record.getCallTime())  + ".mp4")
                .setAnswer(true)
                .setStartRecord(System.currentTimeMillis())
        );

        recorder.startRecord(record.getFileName());

        if (record.isIncoming() && settingUtil.getSetting().isManagerActive()) ActivityUtils.startActivity(context, MainActivity_.class);
    }

    @Override
    public void idleCall(Intent intent) {
        stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.NOT_RINGING));
        recorder.stopRecord();

        Record record = recordDao.last();
        if (record != null){
            record.setEndRecord(System.currentTimeMillis());
            recordDao.update(record);
        }

        if(settingUtil.getSetting().isUnloadActive()) context.startService(new Intent(context, UnloadService.class));
        if(settingUtil.getSetting().isManagerActive()) ActivityUtils.startActivity(context, MainActivity_.class);
    }

    private Record baseRecord(String callPhone) {
        return  Record.emptyRecord()
                .setCallPhone(callPhone)
                .setCallTime(System.currentTimeMillis());
    }

    private String getPhone(Intent intent){
        String callPhone = intent.getExtras().getString("incoming_number");
        return callPhone==null?"Скрытый номер":callPhone.replaceAll(" ", "");
    }
    private boolean checkUSSD(String callNumber) {
        if (callNumber.endsWith("*") || callNumber.endsWith("#")){
            return true;
        }
        return false;
    }
    public static void answerCall(Context context) {
        TelephonyStateDao stateDao = SqlTelephonyStateDao.getInstance(context);

        if (Build.VERSION.SDK_INT >= 21)
            answerCallNewVersions(context, stateDao);
        else
            answerCallOldVersions(context, stateDao);
    }
    public static void endCall(Context context) {
        TelephonyStateDao stateDao = SqlTelephonyStateDao.getInstance(context);
        String state = stateDao.getTelephonyState().getState();

        if (state.equals(TelephonyState.State.RINGING) || state.equals(TelephonyState.State.RECORDING)){
            stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.NOT_RINGING));
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Class classTelephony = Class.forName(telephonyManager.getClass().getName());
                Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
                methodGetITelephony.setAccessible(true);
                Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);
                Class telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
                Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
                methodEndCall.invoke(telephonyInterface);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("RECORD_ERROR", e.toString());
            }
        }
    }

    private static void answerCallOldVersions(Context context, TelephonyStateDao stateDao){
        try {
            Runtime.getRuntime().exec("input keyevent " + Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));

        } catch (IOException e) {
            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));

            context.sendOrderedBroadcast(btnDown, enforcedPerm);
            context.sendOrderedBroadcast(btnUp, enforcedPerm);
        }
        finally {
            stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.RECORDING));
        }
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static void answerCallNewVersions(Context context, TelephonyStateDao stateDao){
        if (stateDao.getTelephonyState().getState().equals(TelephonyState.State.RINGING)){
            try {
                stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.RECORDING));
                for (MediaController mediaController : ((MediaSessionManager) context.getSystemService("media_session")).getActiveSessions(new ComponentName(context, NotificationCall.class))) {
                    if ("com.android.server.telecom".equals(mediaController.getPackageName())) {
                        mediaController.dispatchMediaButtonEvent(new KeyEvent(1, 79));
                        return;
                    }
                }
            } catch (SecurityException e2) {
                e2.printStackTrace();
            }
        }
    }

}
