package com.example.dayslar.newmytalk.network.api;

import com.example.dayslar.newmytalk.db.entity.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenApi {

    @FormUrlEncoded
    @POST("token")
    Call<Token> getToken(@Field("client_id") String talkClient,
                         @Field("client_secret") String secret,
                         @Field("username") String username,
                         @Field("password") String password,
                         @Field("scope") String scope,
                         @Field("grant_type") String grandType);
}
