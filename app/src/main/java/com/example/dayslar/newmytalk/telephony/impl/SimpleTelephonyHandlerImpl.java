package com.example.dayslar.newmytalk.telephony.impl;

import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.database.entity.Record;
import com.example.dayslar.newmytalk.database.impl.SqlRecordDaoImpl;
import com.example.dayslar.newmytalk.database.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.recorder.impl.MediaRecorderImpl;
import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.example.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.example.dayslar.newmytalk.utils.MyLogger;

public class SimpleTelephonyHandlerImpl implements TelephonyHandler {

    private static SimpleTelephonyHandlerImpl instance;

    private RecordDAO recordDAO;
    private Recorder recorder;

    private long idRecord;

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
        recorder = MediaRecorderImpl.getInstance();
    }

    @Override
    public void outgoingCall(Intent intent) {
        String callNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        if (callNumber.endsWith("*") || callNumber.endsWith("#")){
            MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Выполянем ussd запрос" + callNumber);
            return;
        }

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Звоним на " + callNumber);

        idRecord = recordDAO.add(Record.emptyRecord());
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_NUMBER, callNumber);
    }

    @Override
    public void runningCall(Intent intent) {
        String callNumber = intent.getExtras()
                .getString("incoming_number") != null ?
                intent.getExtras().getString("incoming_number"):
                "Скрытый номер";

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок от " + callNumber);
        recordDAO.getCurrentRecord().update(idRecord, RecordTableConfig.CALL_NUMBER, callNumber);
    }

    @Override
    public void offhookCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок");
    }

    @Override
    public void idleCall(Intent intent) {
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");
    }
}
