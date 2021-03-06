package com.dayslar.newmytalk.network.api;

import com.dayslar.newmytalk.db.entity.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenApi {

    @FormUrlEncoded
    @POST("token")
    Call<Token> getToken(@Field("username") String username,
                         @Field("password") String password,
                         @Field("client_id") String clientId,
                         @Field("client_secret") String clientSecret,
                         @Field("scope") String scope,
                         @Field("grant_type") String grandType);

    @FormUrlEncoded
    @POST("token")
    Call<Token> getTokenByRefresh(@Field("client_id") String talkClient,
                                  @Field("client_secret") String secret,
                                  @Field("grant_type") String grandType,
                                  @Field("refresh_token") String refreshToken);
}
