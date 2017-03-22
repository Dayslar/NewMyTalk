package com.dayslar.newmytalk.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dayslar.newmytalk.db.DataBaseController;
import com.dayslar.newmytalk.db.CursorUtils;
import com.dayslar.newmytalk.db.config.DbConfig;
import com.dayslar.newmytalk.db.config.TokenTableConfig;
import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.utils.MyLogger;

public class SqlTokenDao implements TokenDao {

    private static SqlTokenDao instance;
    private SQLiteDatabase database;
    private CursorUtils cursorUtils;

    public static SqlTokenDao getInstance(Context context){
        if (instance == null) {
            synchronized (SqlManagerDao.class) {
                if (instance == null) {
                    instance = new SqlTokenDao(context);
                }
            }
        }

        return instance;
    }

    private SqlTokenDao(Context context) {
        database = DataBaseController.getInstance(context).getDatabase();
        cursorUtils = new CursorUtils();
    }


    @Override
    public long insert(Token token) {
        ContentValues cv = new ContentValues();

        cv.put(TokenTableConfig.ACCESS_TOKEN, token.getAccess_token());
        cv.put(TokenTableConfig.REFRESH_TOKEN, token.getRefresh_token());
        cv.put(TokenTableConfig.EXPIRES_IN, token.getExpires_in());
        cv.put(TokenTableConfig.SCOPE, token.getScope());
        cv.put(TokenTableConfig.TOKEN_TYPE, token.getToken_type());

        long id = database.insert(DbConfig.TOKEN_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись успешно добавлена " + id);

        return id;
    }

    @Override
    public int update(Token token) {
        ContentValues cv = new ContentValues();

        cv.put(TokenTableConfig.ACCESS_TOKEN, token.getAccess_token());
        cv.put(TokenTableConfig.REFRESH_TOKEN, token.getRefresh_token());
        cv.put(TokenTableConfig.EXPIRES_IN, token.getExpires_in());
        cv.put(TokenTableConfig.SCOPE, token.getScope());
        cv.put(TokenTableConfig.TOKEN_TYPE, token.getToken_type());

        int id = database.update(DbConfig.TOKEN_TABLE_NAME, cv, TokenTableConfig.REFRESH_TOKEN  + "= ?", new String[]{token.getRefresh_token()});
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись успешно добавлена " + id);

        return id;
    }

    @Override
    public int delete() {
        return database.delete(DbConfig.TOKEN_TABLE_NAME, null, null);
    }

    @Override
    public Token get() {
        Token token = null;
        Cursor cursor = database.query(DbConfig.TOKEN_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            token = cursorUtils.readToken(cursor);
        }

        return token;
    }
}
