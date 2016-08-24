package com.example.dayslar.newmytalk.db;

import android.database.Cursor;

import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.db.config.RecordTableConfig;
import com.example.dayslar.newmytalk.db.config.TokenTableConfig;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.Token;

public final class CursorUtils {

    public static Record readRecord(Cursor cursor){
        return new Record()
                .setId(cursor.getInt(cursor.getColumnIndex(DbConfig.COLUMN_ID)))
                .setManagerId(cursor.getInt(cursor.getColumnIndex(RecordTableConfig.MANAGER_ID)))
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

    public static Manager readManager(Cursor cursor) {
        return new Manager()
                .setId(cursor.getInt(cursor.getColumnIndex(DbConfig.COLUMN_ID)))
                .setName(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.NAME)))
                .setPhotoPatch(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.PHOTO_PATCH)));
    }

    public static Token readToken(Cursor cursor){
        return new Token()
                .setAccess_token(cursor.getString(cursor.getColumnIndex(TokenTableConfig.ACCESS_TOKEN)))
                .setRefresh_token(cursor.getString(cursor.getColumnIndex(TokenTableConfig.REFRESH_TOKEN)))
                .setExpires_in(cursor.getInt(cursor.getColumnIndex(TokenTableConfig.EXPIRES_IN)))
                .setScope(cursor.getString(cursor.getColumnIndex(TokenTableConfig.SCOPE)))
                .setToken_type(cursor.getString(cursor.getColumnIndex(TokenTableConfig.TOKEN_TYPE)));
    }
}
