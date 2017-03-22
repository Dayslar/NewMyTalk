package com.dayslar.newmytalk.network.service.interfaces;

import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;

import java.util.List;

public interface ManagerService {

    void loadManagers(RetrofitCallback<List<Manager>> callback);
}
