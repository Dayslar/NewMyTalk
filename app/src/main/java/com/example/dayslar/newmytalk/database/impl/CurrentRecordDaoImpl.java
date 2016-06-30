package com.example.dayslar.newmytalk.database.impl;

import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.dao.CurrentRecordDAO;
import com.example.dayslar.newmytalk.entity.Record;

import static com.example.dayslar.newmytalk.database.Utils.readRecord;


public class CurrentRecordDaoImpl implements CurrentRecordDAO {

    private DataBaseController dbController;

    public CurrentRecordDaoImpl(Context context){
        dbController = new DataBaseController(context);
    }

    @Override
    public Record get() {
        Record record = new Record();
        Cursor cursor = dbController.getDatabase().query(DbConfig.CURRENT_RECORD_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            record = readRecord(cursor);
        }

        cursor.close();
        return record;
    }

    @Override
    public void clear() {

    }
}
