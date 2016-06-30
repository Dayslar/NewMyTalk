package com.example.dayslar.newmytalk.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.config.DbConfig;
import com.example.dayslar.newmytalk.database.config.ManagerTableConfig;
import com.example.dayslar.newmytalk.database.dao.ManagerDAO;
import com.example.dayslar.newmytalk.entity.Manager;

import java.util.List;

public class ManagerDAOImpl implements ManagerDAO {

    private DataBaseController dbController;

    public ManagerDAOImpl(Context context){
        dbController = new DataBaseController(context);
    }

    @Override
    public void add(Manager manager) {
        ContentValues cv = new ContentValues();

        cv.put(ManagerTableConfig.MANAGER_ID, manager.getManager_id());
        cv.put(ManagerTableConfig.NAME, manager.getName());
        cv.put(ManagerTableConfig.PHOTO_PATCH, manager.getPhotoPatch());

        long id = dbController.getDatabase().insert(DbConfig.MANAGER_TABLE_NAME, null, cv);
        Log.d("RECORD", "Запись добавлена: " + id);
    }

    @Override
    public void add(List<Manager> managers) {

    }

    @Override
    public void delete(int id) {
        dbController.getDatabase().delete(DbConfig.MANAGER_TABLE_NAME, ManagerTableConfig._ID + "=" + id, null);
        Log.d("RECORD", "Запись с " + ManagerTableConfig.MANAGER_ID + " = " + id + " успешно удалена");
    }

    @Override
    public void delete(List<Integer> listManagerId) {

    }
}
