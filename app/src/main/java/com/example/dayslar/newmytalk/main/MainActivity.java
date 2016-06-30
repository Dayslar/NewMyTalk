package com.example.dayslar.newmytalk.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.database.dao.ManagerDAO;
import com.example.dayslar.newmytalk.database.impl.ManagerDAOImpl;
import com.example.dayslar.newmytalk.entity.Manager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Manager manager = new Manager();

        manager.setManager_id(1);
        manager.setName("Петя петров");
        manager.setPhotoPatch("Вася пупкин фото");

        ManagerDAO mDAO = new ManagerDAOImpl(this);
        mDAO.add(manager);
        mDAO.delete(1);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
