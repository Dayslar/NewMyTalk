package com.example.dayslar.newmytalk.network.service.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlIManagerDao;
import com.example.dayslar.newmytalk.db.impl.SqlITokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.IManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ITokenDao;
import com.example.dayslar.newmytalk.network.api.ManagerApi;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.network.utils.http.code.HttpMessageSelector;
import com.example.dayslar.newmytalk.network.utils.http.code.impls.Http401Message;
import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.example.dayslar.newmytalk.utils.MyLogger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManagerService implements ManagerService {

    private TokenService tokenService;
    private IManagerDao IManagerDao;
    private ITokenDao ITokenDao;
    private ManagerApi managerApi;
    private HttpMessageSelector messageSelector;


    public NetworkManagerService(Context context) {

        this.ITokenDao = SqlITokenDao.getInstance(context);
        this.IManagerDao = SqlIManagerDao.getInstance(context);
        this.managerApi = RetrofitService.getInstance(context).getManagerApi();
        this.messageSelector = HttpMessageSelector.getInstance();
        this.tokenService = new NetworkTokenService(context);
    }

    public void loadManagers(@NonNull final RetrofitCallback<List<Manager>> callback){

        final Token token = ITokenDao.get();

        Call<List<Manager>> call = managerApi.loadManagers(token.getAccess_token());

        callback.onProcess();
        call.enqueue(new Callback<List<Manager>>() {

            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                MyLogger.printDebug(this.getClass(), "Запрос отработал успешно");

                if (response.body() == null){
                    HttpMessage message = messageSelector.getMessage(response.code());
                    if (message instanceof Http401Message)
                        tokenService.loadTokenByRefreshToken(token.getRefresh_token(), new RetrofitCallback<Token>() {
                            @Override
                            public void onProcess() {
                                //TODO
                            }

                            @Override
                            public void onSuccess(Token localToken) {
                                loadManagers(callback);
                            }

                            @Override
                            public void onFailure(HttpMessage httpMessage) {
                                callback.onFailure(httpMessage);
                            }
                        });

                    callback.onFailure(message);
                }

                else {
                    IManagerDao.deleteAll();
                    IManagerDao.insert(response.body());
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                MyLogger.printDebug(this.getClass(), "Не удалось подключиться к серверу");
                callback.onFailure(messageSelector.getMessage(503));
            }
        });
    }
}
