package com.example.dayslar.newmytalk.db.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dayslar.newmytalk.db.DataBaseController;
import com.example.dayslar.newmytalk.db.RecordUtils;
import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.utils.MyLogger;

import java.util.ArrayList;
import java.util.List;

public class SqlRecordDao implements RecordDAO {

    private static SqlRecordDao instance;

    private SQLiteDatabase database;
    private RecordUtils cursorUtils;

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
        cursorUtils = new RecordUtils(context);
    }

    @Override
    public long insert(Record record) {
        long id = database.insert(DbConfig.RECORD_TABLE_NAME, null, cursorUtils.getCvForRecord(record));
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно добавлена");

        return id;
    }

    @Override
    public void update(Record record, long id) {
        database.update(DbConfig.RECORD_TABLE_NAME, cursorUtils.getCvForRecord(record), DbConfig.COLUMN_ID + "= ?", new String[]{id + ""});
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
            record = cursorUtils.readRecord(cursor);
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
                records.add(cursorUtils.readRecord(cursor));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return records;
    }
}
