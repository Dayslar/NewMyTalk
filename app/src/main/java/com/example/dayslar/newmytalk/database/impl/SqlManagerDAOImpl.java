package com.example.dayslar.newmytalk.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.database.interfaces.dao.ManagerDAO;
import com.example.dayslar.newmytalk.database.entity.Manager;

import java.util.ArrayList;
import java.util.List;

import static com.example.dayslar.newmytalk.database.Utils.readManager;

public class SqlManagerDAOImpl implements ManagerDAO {

    private static SqlManagerDAOImpl instance;
    private DataBaseController dbController;

    public static SqlManagerDAOImpl getInstance(Context context){
        if (instance == null) {
            synchronized (SqlManagerDAOImpl.class) {
                if (instance == null) {
                    instance = new SqlManagerDAOImpl(context);
                }
            }
        }

        return instance;
    }

    private SqlManagerDAOImpl(Context context) {
        dbController = DataBaseController.getInstance(context);
    }

    @Override
    public long add(Manager manager) {
        ContentValues cv = new ContentValues();

        cv.put(ManagerTableConfig.MANAGER_ID, manager.getManager_id());
        cv.put(ManagerTableConfig.NAME, manager.getName());
        cv.put(ManagerTableConfig.PHOTO_PATCH, manager.getPhotoPatch());

        long id = dbController.getDatabase().insert(DbConfig.MANAGER_TABLE_NAME, null, cv);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись успешно добавлена " + id);

        return id;
    }

    @Override
    public List<Long> add(List<Manager> managers) {
        List<Long> longManagers = new ArrayList<>();
        for (Manager manager: managers) {
            longManagers.add(add(manager));
        }
        return longManagers;
    }

    @Override
    public void delete(int id) {
        dbController.getDatabase().delete(DbConfig.MANAGER_TABLE_NAME, ManagerTableConfig._ID + "=" + id, null);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись с " + ManagerTableConfig.MANAGER_ID + " = " + id + " успешно удалена");
    }

    @Override
    public void delete(List<Integer> listManagerId) {
        for (Integer id : listManagerId)
            delete(id);
    }

    @Override
    public Manager get(int id) {
        Manager manager = null;
        Cursor cursor = dbController.getDatabase().query(DbConfig.MANAGER_TABLE_NAME, null, "manager_id = ?", new String[]{id + ""}, null, null, null);

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
        Cursor cursor = dbController.getDatabase().query(DbConfig.MANAGER_TABLE_NAME, null, null, null, null, null, null);

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
