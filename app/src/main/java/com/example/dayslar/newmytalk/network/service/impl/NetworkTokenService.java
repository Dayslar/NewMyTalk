package com.example.dayslar.newmytalk.network.service.impl;

import android.content.Context;

import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.example.dayslar.newmytalk.network.api.TokenApi;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.service.TokenConfig;
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
        Call<Token> call = tokenApi.getToken(TokenConfig.CLIENT_ID, TokenConfig.CLIENT_SECRET, username, password, TokenConfig.Scope.WRITE, TokenConfig.GrandType.PASSWORD);

        callback.onProcess();

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

    @Override
    public void loadTokenByRefreshToken(String refreshToken, final RetrofitCallback<Token> callback) {
        final Call<Token> call = tokenApi.getTokenByRefresh(TokenConfig.CLIENT_ID, TokenConfig.CLIENT_SECRET, TokenConfig.GrandType.REFRESH_TOKEN, refreshToken);

        callback.onProcess();

        call.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                MyLogger.printDebug(this.getClass(), "Получили ответ");

                if (response.body() != null){
                    tokenDao.update(response.body());
                    callback.onSuccess(call, response);
                }
                else
                    callback.onFailure(call, new Throwable("Пользователь с такими данными не зарегестрирован"));
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}
