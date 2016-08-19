package com.example.dayslar.newmytalk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.db.config.RecordTableConfig;
import com.example.dayslar.newmytalk.db.config.TokenTableConfig;

class DbHelper extends SQLiteOpenHelper {

    DbHelper(Context context) {
        super(context, DbConfig.DB_NAME, null, DbConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createRecordTable(db, DbConfig.RECORD_TABLE_NAME);
        createManagerTable(db);
        createTokenTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createManagerTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbConfig.MANAGER_TABLE_NAME + "(" +
                DbConfig.COLUMN_ID + " integer primary key, " +
                ManagerTableConfig.NAME + " text, " +
                ManagerTableConfig.PHOTO_PATCH + " text);");
    }

    private void createRecordTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " + tableName + "( " +
                DbConfig.COLUMN_ID + " integer primary key autoincrement," +
                RecordTableConfig.CALL_TIME + " bigint(19)," +
                RecordTableConfig.CALL_PHONE + " text," +
                RecordTableConfig.MY_PHONE + " text," +
                RecordTableConfig.ANSWER + " tinyint(1)," +
                RecordTableConfig.INCOMING + " tinyint(1)," +
                RecordTableConfig.START_RECORD + " bigint(19)," +
                RecordTableConfig.END_RECORD + " bigint(19)," +
                RecordTableConfig.MANAGER_ID + " integer," +
                RecordTableConfig.CONTACT_NAME + " text," +
                RecordTableConfig.FILE_NAME + " text);");
    }

    private void createTokenTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbConfig.TOKEN_TABLE_NAME + "(" +
                TokenTableConfig.ACCESS_TOKEN + " text primary key, " +
                TokenTableConfig.REFRESH_TOKEN + " text, " +
                TokenTableConfig.EXPIRES_IN + " integer, " +
                TokenTableConfig.TOKEN_TYPE + " text, " +
                TokenTableConfig.SCOPE + " text);");
    }
}
