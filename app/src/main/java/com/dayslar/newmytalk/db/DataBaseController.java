package com.dayslar.newmytalk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseController {

    private static DataBaseController instance;
    private SQLiteDatabase database;

    public static DataBaseController getInstance(Context context){
        if (instance == null){
            synchronized (DataBaseController.class){
                if (instance == null){
                    instance = new DataBaseController(context);
                }
            }
        }

        return instance;
    }

    private DataBaseController(Context context){
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public SQLiteDatabase getDatabase() {
        return database;
    }
}
