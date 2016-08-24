package com.example.dayslar.newmytalk.network.api;

import com.example.dayslar.newmytalk.utils.entity.Organization;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrganizationApi {

    @GET("get")
    Call<Organization> loadOrganization(@Query("access_token") String access_token);
}


