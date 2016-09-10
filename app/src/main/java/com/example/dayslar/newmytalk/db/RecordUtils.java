package com.example.dayslar.newmytalk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.RecordTableConfig;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;

public final class RecordUtils {

    private ContentValues cv;
    private ManagerDao managerDao;

    public RecordUtils(Context context){
        cv = new ContentValues();
        managerDao = SqlManagerDao.getInstance(context);
    }

    public Record readRecord(Cursor cursor){
        Manager manager = getManager(cursor);
        return new Record()
                .setId(cursor.getInt(cursor.getColumnIndex(DbConfig.COLUMN_ID)))
                .setManager(manager)
                .setCallPhone(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CALL_PHONE)))
                .setMyPhone(cursor.getString(cursor.getColumnIndex(RecordTableConfig.MY_PHONE)))
                .setCallTime(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.CALL_TIME)))
                .setStartRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.START_RECORD)))
                .setEndRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.END_RECORD)))
                .setContactName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CONTACT_NAME)))
                .setFileName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.FILE_NAME)))
                .setAnswer(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.ANSWER)) == 1)
                .setIncoming(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.INCOMING)) == 1);
    }



    public ContentValues getCvForRecord(Record record){
        cv.clear();
        if (record.getManager() != null) cv.put(RecordTableConfig.MANAGER_ID, record.getManager().getId());
        cv.put(RecordTableConfig.CALL_PHONE, record.getCallPhone());
        cv.put(RecordTableConfig.MY_PHONE, record.getMyPhone());
        cv.put(RecordTableConfig.CALL_TIME, record.getCallTime());
        cv.put(RecordTableConfig.START_RECORD, record.getStartRecord());
        cv.put(RecordTableConfig.END_RECORD, record.getEndRecord());
        cv.put(RecordTableConfig.CONTACT_NAME, record.getContactName());
        cv.put(RecordTableConfig.FILE_NAME, record.getFileName());
        cv.put(RecordTableConfig.ANSWER, record.isAnswer());
        cv.put(RecordTableConfig.INCOMING, record.isIncoming());

        return cv;
    }

    private Manager getManager(Cursor cursor) {
        int managerId = cursor.getInt(cursor.getColumnIndex(RecordTableConfig.MANAGER_ID));
        Manager manager = null;
        if (managerId != 0)
            manager = managerDao.get(managerId);
        return manager;
    }
}
