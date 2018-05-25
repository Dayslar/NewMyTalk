package com.dayslar.newmytalk.network.service.impl;

import android.content.Context;

import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.network.TokenConfig;
import com.dayslar.newmytalk.network.api.TokenApi;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.dayslar.newmytalk.network.service.RetrofitService;
import com.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.dayslar.newmytalk.network.utils.http.code.HttpMessageSelector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkTokenService implements TokenService {

    private TokenApi tokenApi;
    private TokenDao TokenDao;

    private HttpMessageSelector messageSelector;

    public NetworkTokenService(Context context){
        tokenApi = RetrofitService.getInstance(context).getTokenApi();
        TokenDao = SqlTokenDao.getInstance(context);

        messageSelector = HttpMessageSelector.getInstance();
    }

    @Override
    public void loadToken(String username, String password, final RetrofitCallback<Token> callback) {
        Call<Token> call = tokenApi.getToken(username, password, TokenConfig.CLIENT_ID, TokenConfig.CLIENT_SECRET, TokenConfig.Scope.WRITE, TokenConfig.GrandType.PASSWORD);

        callback.onProcess();
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() == null) callback.onFailure(messageSelector.getMessage(response.code()));
                else {
                    TokenDao.delete();
                    TokenDao.insert(response.body());
                    callback.onSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                callback.onFailure(messageSelector.getMessage(503));
            }
        });
    }

    @Override
    public void loadTokenByRefreshToken(String refreshToken, final RetrofitCallback<Token> callback) {
        Call<Token> call = tokenApi.getTokenByRefresh(TokenConfig.CLIENT_ID, TokenConfig.CLIENT_SECRET, TokenConfig.GrandType.REFRESH_TOKEN, refreshToken);

        callback.onProcess();
        call.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() == null) callback.onFailure(messageSelector.getMessage(response.code()));
                else {
                    TokenDao.update(response.body());
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                callback.onFailure(messageSelector.getMessage(503));
            }
        });
    }
}
