package com.example.dayslar.newmytalk.db.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dayslar.newmytalk.db.DataBaseController;
import com.example.dayslar.newmytalk.db.RecordUtils;
import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.interfaces.dao.IRecordDao;
import com.example.dayslar.newmytalk.utils.MyFileUtils;
import com.example.dayslar.newmytalk.utils.MyLogger;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SqlIRecordDao implements IRecordDao {

    private static SqlIRecordDao instance;

    private SQLiteDatabase database;
    private RecordUtils cursorUtils;

    public static SqlIRecordDao getInstance(Context context){
        if (instance == null){
            synchronized (SqlIRecordDao.class){
                if (instance == null)
                    instance = new SqlIRecordDao(context);
            }
        }

        return instance;
    }

    private SqlIRecordDao(Context context) {
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
    public void update(Record record) {
        database.update(DbConfig.RECORD_TABLE_NAME, cursorUtils.getCvForRecord(record), DbConfig.COLUMN_ID + "= ?", new String[]{record.getId() + ""});
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + record.getId() + " успешно обновлена");
    }

    @Override
    public void delete(Record record) {
        this.delete(record.getId(), record.getFileName());
    }

    @Override
    public void deleteAll() {
        database.delete(DbConfig.RECORD_TABLE_NAME, null, null);
        FileUtils.deleteQuietly(new File(MyFileUtils.getFolder()));
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

    private void delete(long id, String fileName){
        database.delete(DbConfig.RECORD_TABLE_NAME,
                DbConfig.COLUMN_ID + " = ?",
                new String[]{id + ""});

        if (fileName != null)
            FileUtils.deleteQuietly(new File(MyFileUtils.getFolder() + fileName));

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + id + " успешно удалена");
    }
}
