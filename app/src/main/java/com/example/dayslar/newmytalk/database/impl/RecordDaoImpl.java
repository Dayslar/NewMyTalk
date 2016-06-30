package com.example.dayslar.newmytalk.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.database.dao.RecordDAO;
import com.example.dayslar.newmytalk.entity.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDAO {

    private DataBaseController dbController;

    public RecordDaoImpl(Context context){
        dbController = new DataBaseController(context);
    }

    @Override
    public void add(Record record) {
        ContentValues cv = new ContentValues();

        cv.put(RecordTableConfig.MANAGER_ID, record.getManager_id());
        cv.put(RecordTableConfig.SUBDIVISION_ID, record.getSubdivision_id());
        cv.put(RecordTableConfig.CALL_NUMBER, record.getCallNumber());
        cv.put(RecordTableConfig.PHONE_NUMBER, record.getPhoneNumber());
        cv.put(RecordTableConfig.CALL_TIME, record.getCallTime());
        cv.put(RecordTableConfig.DURATION, record.getDuration());
        cv.put(RecordTableConfig.CONTACT_NAME, record.getContactName());
        cv.put(RecordTableConfig.FILE_NAME, record.getFileName());
        cv.put(RecordTableConfig.PATCH, record.getPatch());
        cv.put(RecordTableConfig.ANSWER, record.isAnswer());
        cv.put(RecordTableConfig.INCOMING, record.isIncoming());

        long id = dbController.getDatabase().insert(DbConfig.RECORD_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно добавлена");
    }

    @Override
    public void delete(int id) {
        dbController.getDatabase().delete(DbConfig.RECORD_TABLE_NAME,
                RecordTableConfig._ID + " = ?",
                new String[]{id + ""});

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно удалена");
    }

    @Override
    public Record get(int id) {
        Record record = null;
        Cursor cursor = dbController.getDatabase().query(DbConfig.RECORD_TABLE_NAME,
                null,
                RecordTableConfig._ID + " = ?",
                new String[]{id + ""},
                null, null, null);

        if (cursor.moveToFirst()){
            record = readRecord(cursor);
        }
        cursor.close();

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно получена");
        return record;
    }

    @Override
    public List<Record> getRecords() {
        ArrayList<Record> records = new ArrayList<>();
        Cursor cursor = dbController.getDatabase().query(DbConfig.RECORD_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                records.add(readRecord(cursor));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return records;
    }

    private Record readRecord(Cursor cursor){
        Record record = new Record();
        record.set_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig._ID)));
        record.setManager_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.MANAGER_ID)));
        record.setSubdivision_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.SUBDIVISION_ID)));
        record.setCallNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CALL_NUMBER)));
        record.setPhoneNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PHONE_NUMBER)));
        record.setCallTime(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.CALL_TIME)));
        record.setDuration(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.DURATION)));
        record.setContactName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CONTACT_NAME)));
        record.setFileName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.FILE_NAME)));
        record.setPatch(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PATCH)));
        record.setAnswer(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.ANSWER)) == 1);
        record.setIncoming(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.INCOMING)) == 1);


        return record;
    }
}
