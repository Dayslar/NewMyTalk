package com.example.dayslar.newmytalk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.dayslar.newmytalk.R;

public class SettingUtil {

    private static SettingUtil instance;

    private Resources resources;
    private SharedPreferences preferences;

    private Boolean recordingActive;
    private Boolean managerActive;
    private Boolean adminActive;
    private Boolean unloadActive;

    private int delay;
    private String serverPort;
    private String serverIp;


    private SettingUtil(Context context){
        resources = context.getResources();
        preferences = context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());

        loadSetting();
    }

    public static SettingUtil getInstance(Context context){
        if (instance == null) {
            synchronized (SettingUtil.class) {
                if (instance == null)
                    instance = new SettingUtil(context);
            }
        }

        return instance;
    }

    public Boolean isRecordActive() {
        return recordingActive;
    }

    public Boolean getManagerActive() {
        return managerActive;
    }

    public Boolean isManagerActive() {
        return adminActive;
    }

    public Boolean isUnloadActive() {
        return unloadActive;
    }

    public int getDelay() {
        return delay;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void loadSetting(){
        recordingActive = preferences.getBoolean(resources.getString(R.string.chActiveRecordKey), false);
        managerActive = preferences.getBoolean(resources.getString(R.string.chActiveManagerKey), false);
        adminActive = preferences.getBoolean(resources.getString(R.string.chAdminKey), false);
        unloadActive = preferences.getBoolean(resources.getString(R.string.chUnLoadRecordKey), false);

        delay = Integer.parseInt(preferences.getString(resources.getString(R.string.etDelayRecordIsCallKey), "10"));

        serverIp = preferences.getString(resources.getString(R.string.etSettingIpKey), "192.168.21.112");
        serverPort = preferences.getString(resources.getString(R.string.etSettingPortKey), "8080");
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }

    @Override
    public String toString() {
        return "SettingUtil{" +
                "recordingActive=" + recordingActive +
                ", managerActive=" + managerActive +
                ", adminActive=" + adminActive +
                ", unloadActive=" + unloadActive +
                ", delay=" + delay +
                ", serverPort=" + serverPort +
                ", serverIp='" + serverIp + '\'' +
                '}';
    }
}
