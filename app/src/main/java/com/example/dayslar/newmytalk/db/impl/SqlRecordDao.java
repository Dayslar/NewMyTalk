package com.example.dayslar.newmytalk.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.db.DataBaseController;
import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.RecordTableConfig;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.db.entity.Record;

import java.util.ArrayList;
import java.util.List;

import static com.example.dayslar.newmytalk.db.CursurUtils.readRecord;

public class SqlRecordDao implements RecordDao {

    private static SqlRecordDao instance;

    private SQLiteDatabase database;
    private ContentValues cv;

    public static SqlRecordDao getInstance(Context context){
        if (instance == null){
            synchronized (SqlRecordDao.class){
                if (instance == null)
                    instance = new SqlRecordDao(context);
            }
        }

        return instance;
    }

    private SqlRecordDao(Context context) {
        database = DataBaseController.getInstance(context).getDatabase();
        cv = new ContentValues();
    }

    @Override
    public long insert(Record record) {
        cv.clear();

        cv.put(RecordTableConfig.MANAGER_ID, record.getManagerId());
        cv.put(RecordTableConfig.CALL_PHONE, record.getCallPhone());
        cv.put(RecordTableConfig.MY_PHONE, record.getMyPhone());
        cv.put(RecordTableConfig.CALL_TIME, record.getCallTime());
        cv.put(RecordTableConfig.START_RECORD, record.getStartRecord());
        cv.put(RecordTableConfig.END_RECORD, record.getEndRecord());
        cv.put(RecordTableConfig.CONTACT_NAME, record.getContactName());
        cv.put(RecordTableConfig.FILE_NAME, record.getFileName());
        cv.put(RecordTableConfig.ANSWER, record.isAnswer());
        cv.put(RecordTableConfig.INCOMING, record.isIncoming());

        long id = database.insert(DbConfig.RECORD_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно добавлена");

        return id;
    }

    @Override
    public void update(Record record, long id) {
        cv.clear();

        cv.put(RecordTableConfig.MANAGER_ID, record.getManagerId());
        cv.put(RecordTableConfig.CALL_PHONE, record.getCallPhone());
        cv.put(RecordTableConfig.MY_PHONE, record.getMyPhone());
        cv.put(RecordTableConfig.CALL_TIME, record.getCallTime());
        cv.put(RecordTableConfig.START_RECORD, record.getStartRecord());
        cv.put(RecordTableConfig.END_RECORD, record.getEndRecord());
        cv.put(RecordTableConfig.CONTACT_NAME, record.getContactName());
        cv.put(RecordTableConfig.FILE_NAME, record.getFileName());
        cv.put(RecordTableConfig.ANSWER, record.isAnswer());
        cv.put(RecordTableConfig.INCOMING, record.isIncoming());

        database.update(DbConfig.RECORD_TABLE_NAME, cv, DbConfig.COLUMN_ID + "= ?", new String[]{id + ""});
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно обновлена");
    }

    @Override
    public void delete(long id) {
        database.delete(DbConfig.RECORD_TABLE_NAME,
                DbConfig.COLUMN_ID + " = ?",
                new String[]{id + ""});

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно удалена");
    }

    @Override
    public Record get(long id) {
        Record record = null;
        Cursor cursor = database.query(DbConfig.RECORD_TABLE_NAME,
                null,
                DbConfig.COLUMN_ID + " = ?",
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
        Cursor cursor = database.query(DbConfig.RECORD_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                records.add(readRecord(cursor));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return records;
    }
}
