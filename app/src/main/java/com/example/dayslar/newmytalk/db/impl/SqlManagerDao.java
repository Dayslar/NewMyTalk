package com.example.dayslar.newmytalk.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.db.DataBaseController;
import com.example.dayslar.newmytalk.db.config.DbConfig;
import com.example.dayslar.newmytalk.db.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.example.dayslar.newmytalk.db.entity.Manager;

import java.util.ArrayList;
import java.util.List;

import static com.example.dayslar.newmytalk.db.CursorUtils.readManager;

public class SqlManagerDao implements ManagerDao {

    private static SqlManagerDao instance;
    private SQLiteDatabase database;

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
    }

    @Override
    public long insert(Manager manager) {
        ContentValues cv = new ContentValues();

        cv.put(DbConfig.COLUMN_ID, manager.getId());
        cv.put(ManagerTableConfig.NAME, manager.getName());
        cv.put(ManagerTableConfig.PHOTO_PATCH, manager.getPhotoPatch());

        long id = database.insert(DbConfig.MANAGER_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись успешно добавлена " + id);

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
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + DbConfig.COLUMN_ID + " = " + id + " успешно удалена");
    }


    @Override
    public void deleteAll() {
        database.delete(DbConfig.MANAGER_TABLE_NAME, null, null);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Все записи из таблицы менеджеров удалены");
    }

    @Override
    public Manager get(long id) {
        Manager manager = null;
        Cursor cursor = database.query(DbConfig.MANAGER_TABLE_NAME, null, "manager_id = ?", new String[]{id + ""}, null, null, null);

        if (cursor.moveToFirst()) {
            manager = readManager(cursor);
        }

        cursor.close();

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получена запись" + manager);

        return manager;

    }

    @Override
    public List<Manager> getManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        Cursor cursor = database.query(DbConfig.MANAGER_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                managers.add(readManager(cursor));
            }

            while (cursor.moveToNext());

        }

        cursor.close();

        return managers;
    }
}
