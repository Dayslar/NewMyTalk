package com.example.dayslar.newmytalk.telephony.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.database.entity.Record;
import com.example.dayslar.newmytalk.database.impl.SqlRecordDaoImpl;
import com.example.dayslar.newmytalk.database.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.main.MainActivity;
import com.example.dayslar.newmytalk.recorder.impl.MediaRecorderImpl;
import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.example.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.example.dayslar.newmytalk.utils.MyLogger;

public class SimpleTelephonyHandlerImpl implements TelephonyHandler {

    private static SimpleTelephonyHandlerImpl instance;

    private RecordDAO recordDAO;
    private Recorder recorder;
    private Context context;

    private long idRecord;
    private String DIRECTORY = Environment.getExternalStorageDirectory() + "/";

    public static SimpleTelephonyHandlerImpl getInstance(Context context){
        if (instance == null) {
            synchronized (SimpleTelephonyHandlerImpl.class) {
                if (instance == null)
                    instance = new SimpleTelephonyHandlerImpl(context);
            }
        }

        return instance;
    }

    private SimpleTelephonyHandlerImpl(Context context){
        recordDAO = SqlRecordDaoImpl.getInstance(context);
        recorder = initPlayer();

        this.context = context;
    }

    @Override
    public void outgoingCall(Intent intent) {
        String callNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        if (checkUSSD(callNumber)) return;

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Звоним на " + callNumber);

        idRecord = recordDAO.add(Record.emptyRecord());
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_NUMBER, callNumber);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.INCOMING, false);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_TIME, System.currentTimeMillis());
    }

    @Override
    public void runningCall(Intent intent) {
        String callNumber = intent.getExtras()
                .getString("incoming_number") != null ?
                intent.getExtras().getString("incoming_number"):
                "Скрытый номер";


        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок от " + callNumber);

        idRecord = recordDAO.add(Record.emptyRecord());
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.INCOMING, true);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_NUMBER, callNumber);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_TIME, System.currentTimeMillis());

        startActivity(MainActivity.class, 0);
    }

    @Override
    public void offhookCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок");
        recorder.startRecord(DIRECTORY + "record" + idRecord + ".mp4");

        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.PATCH, DIRECTORY);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.FILE_NAME, "record" + idRecord + ".mp4");
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.ANSWER, true);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.START_RECORDING, System.currentTimeMillis());
    }

    @Override
    public void idleCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");

        recorder.stopRecord();
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.END_RECORDING, System.currentTimeMillis());

    }

    private boolean checkUSSD(String callNumber) {
        if (callNumber.endsWith("*") || callNumber.endsWith("#")){
            MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Выполянем ussd запрос" + callNumber);
            return true;
        }
        return false;
    }

    private Recorder initPlayer(){
        Recorder recorder = MediaRecorderImpl.getInstance();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioChannels(1);

        return recorder;
    }

    private void startActivity(final Class<?extends Activity> clazz, final int code) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activityIntent = new Intent(context, clazz);
                activityIntent.putExtra("code", code);
                activityIntent.putExtra("recordID", idRecord);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activityIntent);
            }
        }, 700);

    }

}
