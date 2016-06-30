package com.example.dayslar.newmytalk.database;

import android.database.Cursor;

import com.example.dayslar.newmytalk.database.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.entity.Manager;
import com.example.dayslar.newmytalk.entity.Record;

public class Utils {

    public static Record readRecord(Cursor cursor){
        Record record = new Record();
        record.set_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig._ID)));
        record.setManager_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.MANAGER_ID)));
        record.setSubdivision_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.SUBDIVISION_ID)));
        record.setCallNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CALL_NUMBER)));
        record.setPhoneNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PHONE_NUMBER)));
        record.setCallTime(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.CALL_TIME)));
        record.setStartRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.START_CALL)));
        record.setEndRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.END_CALL)));
        record.setContactName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CONTACT_NAME)));
        record.setFileName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.FILE_NAME)));
        record.setPatch(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PATCH)));
        record.setAnswer(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.ANSWER)) == 1);
        record.setIncoming(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.INCOMING)) == 1);

        return record;
    }

    public static Manager readManager(Cursor cursor) {
        Manager manager = new Manager();
        manager.set_id(cursor.getInt(cursor.getColumnIndex(ManagerTableConfig._ID)));
        manager.setManager_id(cursor.getInt(cursor.getColumnIndex(ManagerTableConfig.MANAGER_ID)));
        manager.setName(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.NAME)));
        manager.setPhotoPatch(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.PHOTO_PATCH)));

        return manager;
    }
}
