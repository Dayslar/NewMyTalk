package com.example.dayslar.newmytalk.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.db.config.TokenTableConfig;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Token;

public final class CursorUtils {

    private ContentValues cv;

    public CursorUtils(){
        cv = new ContentValues();
    }


    public Manager readManager(Cursor cursor) {
        return new Manager()
                .setId(cursor.getInt(cursor.getColumnIndex(DbConfig.COLUMN_ID)))
                .setName(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.NAME)))
                .setPhotoPatch(cursor.getString(cursor.getColumnIndex(ManagerTableConfig.PHOTO_PATCH)));
    }

    public ContentValues getCvForManager(Manager manager) {
        cv.clear();

        cv.put(DbConfig.COLUMN_ID, manager.getId());
        cv.put(ManagerTableConfig.NAME, manager.getName());
        cv.put(ManagerTableConfig.PHOTO_PATCH, manager.getPhotoPatch());

        return cv;
}

    public Token readToken(Cursor cursor){
        return new Token()
                .setAccess_token(cursor.getString(cursor.getColumnIndex(TokenTableConfig.ACCESS_TOKEN)))
                .setRefresh_token(cursor.getString(cursor.getColumnIndex(TokenTableConfig.REFRESH_TOKEN)))
                .setExpires_in(cursor.getInt(cursor.getColumnIndex(TokenTableConfig.EXPIRES_IN)))
                .setScope(cursor.getString(cursor.getColumnIndex(TokenTableConfig.SCOPE)))
                .setToken_type(cursor.getString(cursor.getColumnIndex(TokenTableConfig.TOKEN_TYPE)));
    }
}
