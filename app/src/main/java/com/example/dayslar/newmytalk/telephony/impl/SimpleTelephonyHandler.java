package com.example.dayslar.newmytalk.telephony.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.recorder.impl.SimpleMediaRecorder;
import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.example.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.example.dayslar.newmytalk.ui.activity.MainActivity_;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.SettingUtil;

import java.text.SimpleDateFormat;

public class SimpleTelephonyHandler implements TelephonyHandler {

    private static SimpleTelephonyHandler instance;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

    private RecordDao recordDao;
    private Recorder recorder;
    private Context context;

    private Record record;
    private static SettingUtil settingUtil;

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
        recorder = SimpleMediaRecorder.getInstance();
        settingUtil = SettingUtil.getInstance(context);

        this.context = context;
    }

    @Override
    public void outgoingCall(Intent intent) {
        String callPhone = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        if (checkUSSD(callPhone)) return;
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Звоним на " + callPhone);

        record.setIncoming(false);

    }

    @Override
    public void runningCall(Intent intent) {
        settingUtil.loadSetting();
        String callPhone = intent.getExtras().getString("incoming_number") != null ?
                intent.getExtras().getString("incoming_number")
                : "Скрытый номер";

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок от " + callPhone);

        record = Record.emptyRecord();
        record.setId(recordDao.insert(record));
        record.setIncoming(true);
        record.setCallPhone(callPhone);
        record.setCallTime(System.currentTimeMillis());


        if(settingUtil.getManagerActive())
            startActivity(MainActivity_.class);
    }

    @Override
    public void offhookCall(Intent intent) {
        String callPhone = intent.getExtras().getString("incoming_number") != null ?
                intent.getExtras().getString("incoming_number") : "Скрытый номер";

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок от " + callPhone);

        if (record == null){
            record = Record.emptyRecord();
            record.setId(recordDao.insert(record));
            record.setCallPhone(callPhone);
            record.setCallTime(System.currentTimeMillis());
        }

        record.setFileName(record.getCallPhone() + "_" + sdf.format(record.getCallTime())  + ".mp4");
        record.setAnswer(true);

        recorder.startRecord(record.getFileName());

        record.setStartRecord(System.currentTimeMillis());
    }

    @Override
    public void idleCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");

        recorder.stopRecord();
        record.setEndRecord(System.currentTimeMillis());

        recordDao.update(record, record.getId());
        record = null;
    }

    private boolean checkUSSD(String callNumber) {
        if (callNumber.endsWith("*") || callNumber.endsWith("#")){
            MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Выполянем ussd запрос" + callNumber);
            return true;
        }
        return false;
    }

    private void startActivity(final Class<?extends Activity> clazz) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activityIntent = new Intent(context, clazz);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activityIntent);
            }
        }, 700);

    }

}
