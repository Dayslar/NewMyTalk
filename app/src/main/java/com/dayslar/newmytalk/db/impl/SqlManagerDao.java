package com.dayslar.newmytalk.db.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dayslar.newmytalk.db.CursorUtils;
import com.dayslar.newmytalk.db.DataBaseController;
import com.dayslar.newmytalk.db.config.DbConfig;
import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.db.interfaces.dao.ManagerDao;

import java.util.ArrayList;
import java.util.List;

public class SqlManagerDao implements ManagerDao {

    private static SqlManagerDao instance;
    private SQLiteDatabase database;
    private CursorUtils cursorUtils;

    public static SqlManagerDao getInstance(Context context){
        if (instance == null) {
            synchronized (SqlManagerDao.class) {
                if (instance == null) {
                    instance = new SqlManagerDao(context);
                }
            }
        }

        return instance;
    }

    private SqlManagerDao(Context context) {
        database = DataBaseController.getInstance(context).getDatabase();
        cursorUtils = new CursorUtils();
    }

    @Override
    public long insert(Manager manager) {
        long id = database.insert(DbConfig.MANAGER_TABLE_NAME, null, cursorUtils.getCvForManager(manager));
        return id;
    }

    @Override
    public List<Long> insert(List<Manager> managers) {
        List<Long> longManagers = new ArrayList<>();
        for (Manager manager: managers) {
            longManagers.add(this.insert(manager));
        }
        return longManagers;
    }

    @Override
    public void delete(long id) {
        database.delete(DbConfig.MANAGER_TABLE_NAME, DbConfig.COLUMN_ID + "=" + id, null);
    }


    @Override
    public void deleteAll() {
        database.delete(DbConfig.MANAGER_TABLE_NAME, null, null);
    }

    @Override
    public Manager get(long id) {
        Manager manager = null;
        Cursor cursor = database.query(DbConfig.MANAGER_TABLE_NAME, null, "_id = ?", new String[]{id + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            manager = cursorUtils.readManager(cursor);
        }

        cursor.close();
        return manager;

    }

    @Override
    public List<Manager> getManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        Cursor cursor = database.query(DbConfig.MANAGER_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                managers.add(cursorUtils.readManager(cursor));
            }

            while (cursor.moveToNext());

        }

        cursor.close();

        return managers;
    }
}
