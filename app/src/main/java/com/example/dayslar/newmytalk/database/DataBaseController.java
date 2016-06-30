package com.example.dayslar.newmytalk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseController {

    private SQLiteDatabase database;

    public DataBaseController(Context context){
        database = new DbHelper(context).getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
