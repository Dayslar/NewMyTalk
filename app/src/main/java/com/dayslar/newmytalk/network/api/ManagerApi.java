package com.dayslar.newmytalk.network.api;

import com.dayslar.newmytalk.db.entity.Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ManagerApi {

    @GET("get")
    Call<List<Manager>> loadManagers(@Query("access_token") String access_token);
}


