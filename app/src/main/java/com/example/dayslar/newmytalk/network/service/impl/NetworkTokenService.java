package com.example.dayslar.newmytalk.network.service.impl;

import android.content.Context;

import com.example.dayslar.newmytalk.network.api.TokenApi;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.calback.RetrofitCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkTokenService implements TokenService {

    private TokenApi tokenApi;
    private TokenDao tokenDao;

    public NetworkTokenService(Context context){
        tokenApi = RetrofitService.getInstance(context).getTokenApi();
        tokenDao = SqlTokenDao.getInstance(context);
    }

    @Override
    public void loadToken(String username, String password, final RetrofitCallback<Token> callback) {
        Call<Token> call = tokenApi.getToken("my_talk_rest_client", "my_talk_rest_secret", username, password, "write", "password");
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                MyLogger.printDebug(this.getClass(), "Получили ответ");

                if (response.body() != null){
                    tokenDao.delete();
                    tokenDao.insert(response.body());

                    callback.onSuccess(call, response);
                }
                else
                    callback.onFailure(call, new Throwable("Пользователь с такими данными не зарегестрирован"));

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                MyLogger.printDebug(this.getClass(), "Не достучались до сервера");

                callback.onFailure(call, t);
            }
        });
    }
}
