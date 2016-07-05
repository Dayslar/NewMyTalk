package com.example.dayslar.newmytalk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.database.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.database.impl.SqlRecordDaoImpl;
import com.example.dayslar.newmytalk.database.interfaces.CurrentRecord;
import com.example.dayslar.newmytalk.database.entity.Record;
import com.example.dayslar.newmytalk.utils.MyLogger;

public class TelephoneReceiver extends BroadcastReceiver {

    private static long id;

    @Override
    public void onReceive(Context context, Intent intent) {

        RecordDAO recordDAO = new SqlRecordDaoImpl(context);
        CurrentRecord currentRecord = new SqlRecordDaoImpl(context);

        switch (intent.getAction()) {
            case TelephoneConfig.NEW_OUTGOING_CALL:
                MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Мы звоним");
                id = recordDAO.add(new Record());
                break;

            case TelephoneConfig.PHONE_STATE:
                String phoneState = intent.getStringExtra(TelephoneConfig.EXTRA_STATE);

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_RUNNING)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок");
                    id = recordDAO.add(new Record());
                }

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок");
                    currentRecord.update(id, RecordTableConfig.ANSWER, true);
                    currentRecord.update(id, RecordTableConfig.START_CALL, System.currentTimeMillis());
                }

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_IDLE)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");
                    currentRecord.update(id, RecordTableConfig.END_CALL, System.currentTimeMillis());

                }

                break;
        }
    }
}
