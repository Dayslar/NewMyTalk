package com.dayslar.newmytalk.network.service.impl;

import android.content.Context;

import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.network.api.RecordApi;
import com.dayslar.newmytalk.network.service.RetrofitService;
import com.dayslar.newmytalk.network.service.interfaces.RecordService;
import com.dayslar.newmytalk.utils.MyFileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkRecordService implements RecordService {

    private TokenDao TokenDao;
    private RecordDao RecordDao;
    private RecordApi recordApi;

    public NetworkRecordService(Context context) {
        this.recordApi = RetrofitService.getInstance(context).getRecordApi();
        this.RecordDao = SqlRecordDao.getInstance(context);
        this.TokenDao = SqlTokenDao.getInstance(context);
    }

    @Override
    public void sendRecord(final Record record) {
        final Token token = TokenDao.get();

        recordApi.sendRecord(token.getAccess_token(), record).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    RecordDao.delete(record);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    @Override
    public void sendRecordAndFile(final Record record) {
        final Token token = TokenDao.get();

        ObjectMapper objectMapper = new ObjectMapper();
        String recordDataValue = null;
        try {
            recordDataValue = objectMapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        File recordFile = new File(MyFileUtils.getFolder() + record.getFileName());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), recordFile);
        RequestBody recordData = RequestBody.create(MediaType.parse("multipart/form-data"), recordDataValue);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", recordFile.getName(), requestFile);


        recordApi.sendRecordAndFile(token.getAccess_token(), recordData, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    RecordDao.delete(record);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
