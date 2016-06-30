package com.example.dayslar.newmytalk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.database.impl.ManagerDAOImpl;
import com.example.dayslar.newmytalk.entity.Manager;

import org.junit.runner.RunWith;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ManagersTest {

    @Test
    public void addManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        Manager manager = new Manager();
        manager.setManager_id(2);
        manager.setName("Питюня");
        manager.setPhotoPatch("Фотка питюни");

        new ManagerDAOImpl(appContext).add(manager);
    }

    @Test
    public void addManagers() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();

        List<Manager> managers = new ArrayList<>();

        Manager manager1 = new Manager();
        manager1.setManager_id(1);
        manager1.setName("Вася");
        manager1.setPhotoPatch("Фото Васи");
        managers.add(manager1);

        Manager manager2 = new Manager();
        manager1.setManager_id(2);
        manager1.setName("Петя");
        manager1.setPhotoPatch("Фото Петя");
        managers.add(manager2);

        Manager manager3 = new Manager();
        manager1.setManager_id(3);
        manager1.setName("Игорь");
        manager1.setPhotoPatch("Фото Игорь");
        managers.add(manager3);

        new ManagerDAOImpl(appContext).add(managers);


    }

    @Test
    public void deleteManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new ManagerDAOImpl(appContext).delete(4);
    }


    @Test
    public void getManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new ManagerDAOImpl(appContext).get(2);
    }

    @Test
    public void getManagers() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new ManagerDAOImpl(appContext).getManagers();
    }
}
