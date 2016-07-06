package com.example.dayslar.newmytalk.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.database.interfaces.CurrentRecord;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.database.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.database.entity.Record;

import java.util.ArrayList;
import java.util.List;

import static com.example.dayslar.newmytalk.database.Utils.readRecord;

public class SqlRecordDaoImpl implements RecordDAO, CurrentRecord {

    private static SqlRecordDaoImpl instance;

    private DataBaseController dbController;
    private ContentValues cv;

    public static SqlRecordDaoImpl getInstance(Context context){
        if (instance == null){
            synchronized (SqlRecordDaoImpl.class){
                if (instance == null)
                    instance = new SqlRecordDaoImpl(context);
            }
        }

        return instance;
    }

    private SqlRecordDaoImpl(Context context) {
        dbController = DataBaseController.getInstance(context);
        cv = new ContentValues();
    }

    @Override
    public long add(Record record) {
        cv.clear();

        cv.put(RecordTableConfig.MANAGER_ID, record.getManager_id());
        cv.put(RecordTableConfig.SUBDIVISION_ID, record.getSubdivision_id());
        cv.put(RecordTableConfig.CALL_NUMBER, record.getCallNumber());
        cv.put(RecordTableConfig.PHONE_NUMBER, record.getPhoneNumber());
        cv.put(RecordTableConfig.CALL_TIME, record.getCallTime());
        cv.put(RecordTableConfig.START_RECORDING, record.getStartRecord());
        cv.put(RecordTableConfig.END_RECORDING, record.getEndRecord());
        cv.put(RecordTableConfig.CONTACT_NAME, record.getContactName());
        cv.put(RecordTableConfig.FILE_NAME, record.getFileName());
        cv.put(RecordTableConfig.PATCH, record.getPatch());
        cv.put(RecordTableConfig.ANSWER, record.isAnswer());
        cv.put(RecordTableConfig.INCOMING, record.isIncoming());

        long id = dbController.getDatabase().insert(DbConfig.RECORD_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно добавлена");

        return id;
    }

    @Override
    public void delete(long id) {
        dbController.getDatabase().delete(DbConfig.RECORD_TABLE_NAME,
                RecordTableConfig._ID + " = ?",
                new String[]{id + ""});

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно удалена");
    }

    @Override
    public Record get(long id) {
        Record record = null;
        Cursor cursor = dbController.getDatabase().query(DbConfig.RECORD_TABLE_NAME,
                null,
                RecordTableConfig._ID + " = ?",
                new String[]{id + ""},
                null, null, null);

        if (cursor.moveToFirst()) {
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

    @Override
    public CurrentRecord getCurrentRecord() {
        return this;
    }

    @Override
    public void update(long recordId, String column, Object value) {
        cv.clear();

        if (value instanceof Integer)
            cv.put(column, (int) value);
        else if (value instanceof Long)
            cv.put(column, (long) value);
        else if (value instanceof Boolean)
            cv.put(column, (Boolean) value);
        else if (value instanceof String)
            cv.put(column, (String) value);

        dbController.getDatabase().update(DbConfig.RECORD_TABLE_NAME, cv, RecordTableConfig._ID + "= ?", new String[]{recordId + ""});
    }
}
