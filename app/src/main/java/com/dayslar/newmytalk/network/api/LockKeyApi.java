package com.dayslar.newmytalk.network.api;

import com.dayslar.newmytalk.utils.entity.LockKey;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LockKeyApi {

    @GET("get")
    Call<LockKey> getKey(@Query("access_token") String accessToken);
}
