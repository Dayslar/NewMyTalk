package com.example.dayslar.newmytalk.network.api;

import com.example.dayslar.newmytalk.db.entity.Record;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RecordApi {

    @Multipart
    @POST("add-to-file")
    Call<ResponseBody> sendRecordAndFile(@Query("access_token") String accessToken, @Part("recordData") RequestBody recordData, @Part MultipartBody.Part file);

    @POST("add")
    Call<ResponseBody> sendRecord(@Query("access_token") String accessToken, @Body Record record);
}
