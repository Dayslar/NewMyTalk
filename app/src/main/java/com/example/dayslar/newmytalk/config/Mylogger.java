package com.example.dayslar.newmytalk.config;

import android.util.Log;

public  class MyLogger {

    public static final String LOG_TEST = "RECORD_LOG_TEST";
    public static final String LOG_DEBUG = "RECORD_LOG_DEBUG";
    public static final String LOG_RELEASE = "RECORD_LOG_RELEASE";


    public static void print(Class clazz, String logLevel,  String message){
        Log.d(logLevel, clazz + " : " + message);
    }
}
