package com.example.dayslar.newmytalk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.database.config.RecordTableConfig;

class DbHelper extends SQLiteOpenHelper {

    DbHelper(Context context) {
        super(context, DbConfig.DB_NAME, null, DbConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createRecordTable(db, DbConfig.RECORD_TABLE_NAME);
        createManagerTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createManagerTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbConfig.MANAGER_TABLE_NAME + "(" +
                ManagerTableConfig._ID + " integer primary key autoincrement," +
                ManagerTableConfig.NAME + " text, " +
                ManagerTableConfig.MANAGER_ID + " integer, " +
                ManagerTableConfig.PHOTO_PATCH + " text);");
    }

    private void createRecordTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " + tableName + "( " +
                RecordTableConfig._ID + " integer primary key autoincrement," +
                RecordTableConfig.PATCH + " text," +
                RecordTableConfig.FILE_NAME + " text," +
                RecordTableConfig.PHONE_NUMBER + " text," +
                RecordTableConfig.CALL_NUMBER + " text," +
                RecordTableConfig.START_CALL + " bigint(19)," +
                RecordTableConfig.END_CALL + " bigint(19)," +
                RecordTableConfig.CALL_TIME + " bigint(19)," +
                RecordTableConfig.CONTACT_NAME + " text," +
                RecordTableConfig.MANAGER_ID + " integer," +
                RecordTableConfig.ANSWER + " tinyint(1)," +
                RecordTableConfig.INCOMING + " tinyint(1)," +
                RecordTableConfig.SUBDIVISION_ID + " integer);");
    }
}
