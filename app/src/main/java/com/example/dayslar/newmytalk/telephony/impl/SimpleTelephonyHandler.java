package com.example.dayslar.newmytalk.telephony.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;

import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.TelephonyState;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.example.dayslar.newmytalk.recorder.impl.SimpleMediaRecorder;
import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.example.dayslar.newmytalk.services.UnloadService;
import com.example.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.example.dayslar.newmytalk.ui.activity.DialogActivity_;
import com.example.dayslar.newmytalk.ui.activity.MainActivity_;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.SettingUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class SimpleTelephonyHandler implements TelephonyHandler {

    private static SimpleTelephonyHandler instance;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    private RecordDao recordDao;
    private TelephonyStateDao stateDao;
    private ManagerDao managerDao;
    private Recorder recorder;
    private Context context;

    private Record record;
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
        this.managerDao = SqlManagerDao.getInstance(context);
        this.recordDao = SqlRecordDao.getInstance(context);
        this.recorder = SimpleMediaRecorder.getInstance(context);
        this.stateDao = SqlTelephonyStateDao.getInstance(context);

        this.settingUtil = SettingUtil.getInstance(context);

        this.context = context;
    }

    @Override
    public void outgoingCall(Intent intent) {
        String callPhone = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER").replaceAll(" ", "");

        if (checkUSSD(callPhone)) return;
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Звоним на " + callPhone);

        if (record == null) initBaseRecord(callPhone);
        record.setIncoming(false);

    }

    @Override
    public void runningCall(Intent intent) {
        settingUtil.loadSetting();

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок от " + getPhone(intent));

        if (record == null) initBaseRecord(getPhone(intent));

        record.setIncoming(true);
        recordDao.update(record);

        stateDao.setTelephonyState(new TelephonyState()
                        .setState(TelephonyState.State.RINGING)
                        .setRecordId(record.getId())
        );

        if(settingUtil.getSetting().isManagerActive()) startActivity(MainActivity_.class);
    }

    @Override
    public void offhookCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок от " + getPhone(intent));

        if (record == null) initBaseRecord(getPhone(intent));

        stateDao.setTelephonyState(new TelephonyState()
                .setState(TelephonyState.State.RECORDING)
                .setRecordId(record.getId()));

        record.setFileName(record.getCallPhone() + "_" + sdf.format(record.getCallTime())  + ".mp4");
        record.setAnswer(true);
        record.setStartRecord(System.currentTimeMillis());

        recorder.startRecord(record.getFileName());

        if (record.isIncoming() && settingUtil.getSetting().isManagerActive()) startActivity(DialogActivity_.class);
    }

    @Override
    public void idleCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");

        stateDao.setTelephonyState(new TelephonyState().setState(TelephonyState.State.NOT_RINGING));

        recorder.stopRecord();
        record.setEndRecord(System.currentTimeMillis());

        recordDao.update(record);

        record = null;

        if(settingUtil.getSetting().isUnloadActive()) context.startService(new Intent(context, UnloadService.class));
        if(settingUtil.getSetting().isManagerActive()) startActivity(MainActivity_.class);
    }

    @Override
    public void setManagerInfo(int managerId) {
        Manager manager = managerDao.get(managerId);
        record.setManager(manager);
    }

    private void initBaseRecord(String callPhone) {
        record = Record.emptyRecord();
        record.setId(recordDao.insert(record));
        record.setCallPhone(callPhone);
        record.setCallTime(System.currentTimeMillis());
    }

    private String getPhone(Intent intent){
        String callPhone = intent.getExtras().getString("incoming_number");
        return callPhone==null?"Скрытый номер":callPhone.replaceAll(" ", "");
    }

    private boolean checkUSSD(String callNumber) {
        if (callNumber.endsWith("*") || callNumber.endsWith("#")){
            MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Выполянем ussd запрос" + callNumber);
            return true;
        }
        return false;
    }

    private void startActivity(final Class<? extends Activity> activity) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activityIntent = new Intent(context,activity);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(activityIntent);
            }
        }, 500);

    }

    public static void answerCall(Context context) {
        MyLogger.printDebug(SimpleTelephonyHandler.class, "На звонок ответили");

        try {
            Runtime.getRuntime().exec("input keyevent " + Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));

        } catch (IOException e) {
            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));

            context.sendOrderedBroadcast(btnDown, enforcedPerm);
            context.sendOrderedBroadcast(btnUp, enforcedPerm);
        }

    }

    public static void endCall(){
        try {
            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder rebind = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, rebind);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("RECORD_ERROR", e.toString());
        }
    }

}
