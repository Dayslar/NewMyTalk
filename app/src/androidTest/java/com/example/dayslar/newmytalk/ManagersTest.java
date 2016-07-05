package com.example.dayslar.newmytalk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.database.impl.SqlManagerDAOImpl;
import com.example.dayslar.newmytalk.database.entity.Manager;
import com.example.dayslar.newmytalk.database.interfaces.dao.ManagerDAO;

import org.junit.runner.RunWith;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ManagersTest {

    @Test
    public void addManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDAO managerDAO = SqlManagerDAOImpl.getInstance(appContext);

        Manager manager = new Manager()
                .setManager_id(2)
                .setName("Питюня")
                .setPhotoPatch("Фотка питюни");

        managerDAO.add(manager);
    }

    @Test
    public void addManagers() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDAO managerDAO = SqlManagerDAOImpl.getInstance(appContext);

        List<Manager> managers = new ArrayList<>();

        Manager manager1 = new Manager()
                .setManager_id(1)
                .setName("Вася")
                .setPhotoPatch("Фото Васи");
        managers.add(manager1);

        Manager manager2 = new Manager()
                .setManager_id(2)
                .setName("Петя")
                .setPhotoPatch("Фото Петя");
        managers.add(manager2);

        Manager manager3 = new Manager()
                .setManager_id(3)
                .setName("Игорь")
                .setPhotoPatch("Фото Игорь");
        managers.add(manager3);

        managerDAO.add(managers);


    }

    @Test
    public void deleteManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDAO managerDAO = SqlManagerDAOImpl.getInstance(appContext);

        managerDAO.delete(4);
    }


    @Test
    public void getManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDAO managerDAO = SqlManagerDAOImpl.getInstance(appContext);

        managerDAO.get(2);
    }

    @Test
    public void getManagers() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ManagerDAO managerDAO = SqlManagerDAOImpl.getInstance(appContext);

        managerDAO.getManagers();
    }
}
