package com.dayslar.newmytalk.network.service.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.network.api.ManagerApi;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.dayslar.newmytalk.network.service.RetrofitService;
import com.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.dayslar.newmytalk.network.utils.http.code.HttpMessageSelector;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http401Message;
import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManagerService implements ManagerService {

    private TokenService tokenService;
    private ManagerDao ManagerDao;
    private TokenDao TokenDao;
    private ManagerApi managerApi;
    private HttpMessageSelector messageSelector;


    public NetworkManagerService(Context context) {

        this.TokenDao = SqlTokenDao.getInstance(context);
        this.ManagerDao = SqlManagerDao.getInstance(context);
        this.managerApi = RetrofitService.getInstance(context).getManagerApi();
        this.messageSelector = HttpMessageSelector.getInstance();
        this.tokenService = new NetworkTokenService(context);
    }

    public void loadManagers(@NonNull final RetrofitCallback<List<Manager>> callback){

        final Token token = TokenDao.get();

        Call<List<Manager>> call = managerApi.loadManagers(token.getAccess_token());

        callback.onProcess();
        call.enqueue(new Callback<List<Manager>>() {

            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
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
                    ManagerDao.deleteAll();
                    ManagerDao.insert(response.body());
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                callback.onFailure(messageSelector.getMessage(503));
            }
        });
    }
}
