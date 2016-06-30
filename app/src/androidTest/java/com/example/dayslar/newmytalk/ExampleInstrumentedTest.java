package com.example.dayslar.newmytalk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.database.impl.ManagerDAOImpl;
import com.example.dayslar.newmytalk.entity.Manager;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

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
    public void deleteManager() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new ManagerDAOImpl(appContext).delete(11);
    }
}
