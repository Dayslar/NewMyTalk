package com.example.dayslar.newmytalk.network.api;

import com.example.dayslar.newmytalk.utils.LockKey;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LockKeyApi {

    @GET("get")
    Call<LockKey> getKey(@Query("access_token") String accessToken);
}
