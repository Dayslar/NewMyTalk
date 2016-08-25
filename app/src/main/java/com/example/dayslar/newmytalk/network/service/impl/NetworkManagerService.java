package com.example.dayslar.newmytalk.network.service.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.example.dayslar.newmytalk.network.api.ManagerApi;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.calback.RetrofitCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManagerService implements ManagerService{

    private TokenService tokenService;
    private ManagerDao managerDao;
    private TokenDao tokenDao;
    private ManagerApi managerApi;

    public NetworkManagerService(Context context) {

        this.tokenDao = SqlTokenDao.getInstance(context);
        this.managerDao = SqlManagerDao.getInstance(context);
        this.managerApi = RetrofitService.getInstance(context).getManagerApi();
        this.tokenService = new NetworkTokenService(context);
    }

    public void loadManagers(@NonNull final RetrofitCallback<List<Manager>> callback){

        final Token token = tokenDao.get();

        Call<List<Manager>> call = managerApi.loadManagers(token.getAccess_token());

        callback.onProcess();
        call.enqueue(new Callback<List<Manager>>() {

            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {

                final Call<List<Manager>> listCall = call;

                MyLogger.printDebug(this.getClass(), "Запрос отработал успешно");
                if (response.body() == null){
                    if (response.code() == 401){
                        tokenService.loadTokenByRefreshToken(token.getRefresh_token(), new RetrofitCallback<Token>() {
                            @Override
                            public void onProcess() {
                                //TODO
                            }

                            @Override
                            public void onSuccess(Call<Token> call, Response<Token> response) {
                                if (response.body() != null)
                                    loadManagers(callback);
                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable e) {
                                callback.onFailure(listCall, e);
                            }
                        });
                    }
                    else
                        callback.onFailure(call, new Throwable("Не найдено менеджеров"));
                }

                else {
                    managerDao.deleteAll();
                    managerDao.insert(response.body());
                    callback.onSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                MyLogger.printDebug(this.getClass(), "Не удалось подключиться к серверу");
                callback.onFailure(call, t);
            }
        });
    }
}
