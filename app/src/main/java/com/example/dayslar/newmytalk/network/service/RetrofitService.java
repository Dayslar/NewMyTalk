package com.example.dayslar.newmytalk.network.service;


import android.content.Context;

import com.example.dayslar.newmytalk.network.api.LockKeyApi;
import com.example.dayslar.newmytalk.network.api.ManagerApi;
import com.example.dayslar.newmytalk.network.api.OrganizationApi;
import com.example.dayslar.newmytalk.network.api.TokenApi;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.example.dayslar.newmytalk.utils.SettingUtil;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitService {
    private static RetrofitService instance;

    private TokenApi tokenApi;
    private ManagerApi managerApi;
    private OrganizationApi organizationApi;
    private LockKeyApi lockKeyApi;


    private SettingUtil settingUtil;

    private RetrofitService(Context context){
        settingUtil = SettingUtil.getInstance(context);
    }

    public static RetrofitService getInstance(Context context){
        if (instance == null) {
            synchronized (SimpleTelephonyHandler.class) {
                if (instance == null)
                    instance = new RetrofitService(context);
            }
        }

        return instance;
    }


    public ManagerApi getManagerApi(){
        if (managerApi == null)
            initManagerApi();

        return managerApi;
    }

    public OrganizationApi getOrganizationApi(){
        if (organizationApi == null)
            initOrganizationApi();
        return organizationApi;
    }

    public TokenApi getTokenApi(){
        if (tokenApi == null)
            initAccessTokenApi();
        return tokenApi;
    }

    public LockKeyApi getLockKeyApi() {
        if (lockKeyApi == null){
            initLockKeyApi();
        }
        return lockKeyApi;
    }

    private void initLockKeyApi() {
        Retrofit retrofitManager = new Retrofit.Builder()
                .baseUrl("http://" + settingUtil.getServerIp() + ":" + settingUtil.getServerPort() + "/app/api/user/lock-key/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        lockKeyApi = retrofitManager.create(LockKeyApi.class);
    }


    private void initManagerApi() {
        Retrofit retrofitManager = new Retrofit.Builder()
                .baseUrl("http://" + settingUtil.getServerIp() + ":" + settingUtil.getServerPort() + "/app/api/user/manager/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        managerApi = retrofitManager.create(ManagerApi.class);
    }

    private void initOrganizationApi() {
        Retrofit retrofitManager = new Retrofit.Builder()
                .baseUrl("http://" + settingUtil.getServerIp() + ":" + settingUtil.getServerPort() + "/app/api/user/organization/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        organizationApi = retrofitManager.create(OrganizationApi.class);
    }

    private void initAccessTokenApi() {
        Retrofit retrofitManager = new Retrofit.Builder()
                .baseUrl("http://" + settingUtil.getServerIp() + ":" + settingUtil.getServerPort() + "/app/oauth/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tokenApi = retrofitManager.create(TokenApi.class);
    }

}
