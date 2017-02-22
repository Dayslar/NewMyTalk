package com.example.dayslar.newmytalk.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dayslar.newmytalk.db.DataBaseController;
import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.TelephonyStateTableConfig;
import com.example.dayslar.newmytalk.db.entity.TelephonyState;
import com.example.dayslar.newmytalk.db.interfaces.dao.ITelephonyStateDao;

public class SqlITelephonyStateDao implements ITelephonyStateDao {

    private static SqlITelephonyStateDao instance;
    private SQLiteDatabase database;
    private ContentValues cv;

    public static SqlITelephonyStateDao getInstance(Context context){
        if (instance == null) {
            synchronized (SqlIManagerDao.class) {
                if (instance == null) {
                    instance = new SqlITelephonyStateDao(context);
                }
            }
        }

        return instance;
    }

    private SqlITelephonyStateDao(Context context) {
        this.database = DataBaseController.getInstance(context).getDatabase();
        this.cv = new ContentValues();
    }


    @Override
    public TelephonyState getTelephonyState() {
        TelephonyState state = null;
        Cursor cursor = database.query(DbConfig.TELEPHONY_STATE_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            state =  new TelephonyState()
                    .setState(cursor.getString(cursor.getColumnIndex(TelephonyStateTableConfig.STATE)))
                    .setRecordId(cursor.getLong(cursor.getColumnIndex(TelephonyStateTableConfig.RECORD_ID)));
        }

        else  {
            initState();
            state = this.getTelephonyState();
        }

        return state;
    }

    @Override
    public void initState() {
        cv.clear();
        cv.put(TelephonyStateTableConfig.STATE, TelephonyState.State.NOT_RINGING);

        database.delete(DbConfig.TELEPHONY_STATE_TABLE_NAME, null, null);
        database.insert(DbConfig.TELEPHONY_STATE_TABLE_NAME, null, cv);
    }

    @Override
    public void setTelephonyState(TelephonyState state) {
        cv.clear();
        cv.put(TelephonyStateTableConfig.STATE, state.getState());
        cv.put(TelephonyStateTableConfig.RECORD_ID, state.getRecordId());

        database.update(DbConfig.TELEPHONY_STATE_TABLE_NAME, cv, null, null);
    }
}
