package com.example.dayslar.newmytalk.network.service.impl;

import android.content.Context;
import android.os.Environment;

import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDAO;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDAO;
import com.example.dayslar.newmytalk.network.api.RecordApi;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.service.interfaces.RecordService;
import com.example.dayslar.newmytalk.utils.MyLogger;
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

    private TokenDAO tokenDAO;
    private RecordDAO recordDao;
    private RecordApi recordApi;

    public NetworkRecordService(Context context) {
        this.recordApi = RetrofitService.getInstance(context).getRecordApi();
        this.recordDao = SqlRecordDao.getInstance(context);
        this.tokenDAO = SqlTokenDao.getInstance(context);
    }

    @Override
    public void sendRecord(final Record record) {
        final Token token = tokenDAO.get();

        recordApi.sendRecord(token.getAccess_token(), record).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLogger.printDebug(this.getClass(), "RECORD FILE TRANSFER SUCCESS CODE: " + response.code());

                if (response.code() == 200)
                    recordDao.delete(record);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLogger.printDebug(this.getClass(), "RECORD FILE TRANSFER FAILURE: " + t.getMessage());
            }
        });
    }

    @Override
    public void sendRecordAndFile(final Record record) {
        final Token token = tokenDAO.get();

        ObjectMapper objectMapper = new ObjectMapper();
        String recordDataValue = null;
        try {
            recordDataValue = objectMapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        File recordFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.MyRecord/" + record.getFileName());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), recordFile);
        RequestBody recordData = RequestBody.create(MediaType.parse("multipart/form-data"), recordDataValue);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", recordFile.getName(), requestFile);


        recordApi.sendRecordAndFile(token.getAccess_token(), recordData, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLogger.printDebug(this.getClass(), "RECORD FILE TRANSFER SUCCESS CODE: " + response.code());
                if (response.code() == 200)
                    recordDao.delete(record);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLogger.printDebug(this.getClass(), "RECORD FILE TRANSFER FAILURE: " + t.getMessage());
            }
        });
    }
}
