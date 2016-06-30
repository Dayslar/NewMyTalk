package com.example.dayslar.newmytalk.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.dao.CurrentRecordDAO;
import com.example.dayslar.newmytalk.database.interfaces.CurrentRecord;
import com.example.dayslar.newmytalk.entity.Record;

import static com.example.dayslar.newmytalk.database.Utils.readRecord;


public class CurrentRecordDaoImpl implements CurrentRecordDAO, CurrentRecord {

    private DataBaseController dbController;
    private ContentValues cv;

    public CurrentRecordDaoImpl(Context context){
        dbController = new DataBaseController(context);
        cv = new ContentValues();
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

    @Override
    public void updateInt(String column, int value) {
        cv.clear();

        cv.put(column,  value);
        dbUpdate();
    }

    @Override
    public void updateLong(String column, long value) {
        cv.clear();

        cv.put(column, value);
        dbUpdate();
    }

    @Override
    public void updateString(String column, String value) {
        cv.clear();

        cv.put(column, value);
        dbUpdate();
    }

    @Override
    public void updateBoolean(String column, boolean value) {
        cv.clear();

        cv.put(column, value);
        dbUpdate();
    }

    private void dbUpdate(){
        dbController.getDatabase().update(DbConfig.CURRENT_RECORD_TABLE_NAME, cv, null, null);
    }
}
