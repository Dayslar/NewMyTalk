package com.example.dayslar.newmytalk.database;

import android.database.Cursor;

import com.example.dayslar.newmytalk.database.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.database.config.RecordTableConfig;
import com.example.dayslar.newmytalk.entity.Manager;
import com.example.dayslar.newmytalk.entity.Record;

public class Utils {

    public static Record readRecord(Cursor cursor){
        return new Record()
                .set_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig._ID)))
                .setManager_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.MANAGER_ID)))
                .setSubdivision_id(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.SUBDIVISION_ID)))
                .setCallNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CALL_NUMBER)))
                .setPhoneNumber(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PHONE_NUMBER)))
                .setCallTime(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.CALL_TIME)))
                .setStartRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.START_CALL)))
                .setEndRecord(cursor.getLong(cursor.getColumnIndex(RecordTableConfig.END_CALL)))
                .setContactName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.CONTACT_NAME)))
                .setFileName(cursor.getString(cursor.getColumnIndex(RecordTableConfig.FILE_NAME)))
                .setPatch(cursor.getString(cursor.getColumnIndex(RecordTableConfig.PATCH)))
                .setAnswer(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.ANSWER)) == 1)
                .setIncoming(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.INCOMING)) == 1);
    }

    public static Manager readManager(Cursor cursor) {
        return new Manager()
                .set_id(cursor.getInt(cursor.getColumnIndex(ManagerTableConfig._ID)))
                .setManager_id(cursor.getInt(cursor.getColumnIndex(ManagerTableConfig.MANAGER_ID)))
                .setName(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.NAME)))
                .setPhotoPatch(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.PHOTO_PATCH)));
    }
}
