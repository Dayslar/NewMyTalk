package com.example.dayslar.newmytalk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.utils.MyLogger;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.main_activity)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Активите создана заново");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Активите не пересоздавала второй раз");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
