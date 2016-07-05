package com.example.dayslar.newmytalk.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.database.interfaces.dao.ManagerDAO;
import com.example.dayslar.newmytalk.database.impl.ManagerDAOImpl;
import com.example.dayslar.newmytalk.entity.Manager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

}
