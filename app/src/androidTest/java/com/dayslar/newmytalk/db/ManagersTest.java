package com.dayslar.newmytalk.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.dayslar.newmytalk.db.interfaces.dao.ManagerDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ManagersTest {

    @Test
    public void addManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        Manager manager = new Manager()
                .setName("Питюня")
                .setPhotoPatch("Фотка питюни");

        ManagerDao.insert(manager);
    }

    @Test
    public void addManagers() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        List<Manager> managers = new ArrayList<>();

        Manager manager1 = new Manager()
                .setId(1)
                .setName("Вася")
                .setPhotoPatch("Фото Васи");
        managers.add(manager1);

        Manager manager2 = new Manager()
                .setId(2)
                .setName("Петя")
                .setPhotoPatch("Фото Петя");
        managers.add(manager2);

        Manager manager3 = new Manager()
                .setId(3)
                .setName("Игорь")
                .setPhotoPatch("Фото Игорь");
        managers.add(manager3);

        ManagerDao.insert(managers);


    }

    @Test
    public void getManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        ManagerDao.get(2);
    }

    @Test
    public void getManagers() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        ManagerDao.getManagers();
    }

    @Test
    public void deleteManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        ManagerDao.delete(4);
    }

    @Test
    public void deleteManagers() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDao ManagerDao = SqlManagerDao.getInstance(appContext);

        ManagerDao.deleteAll();
    }


}
