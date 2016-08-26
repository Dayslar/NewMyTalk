package com.example.dayslar.newmytalk.network.service.interfaces;

import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;

import java.util.List;

public interface ManagerService {

    void loadManagers(RetrofitCallback<List<Manager>> callback);
}
