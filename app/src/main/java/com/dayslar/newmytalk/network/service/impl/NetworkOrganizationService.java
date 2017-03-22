package com.dayslar.newmytalk.network.service.impl;

import android.content.Context;

import com.dayslar.newmytalk.network.api.OrganizationApi;
import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.network.service.RetrofitService;
import com.dayslar.newmytalk.network.service.interfaces.OrganizationService;
import com.dayslar.newmytalk.utils.entity.Organization;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkOrganizationService implements OrganizationService {

    private OrganizationApi organizationApi;
    private TokenDao TokenDao;

    public NetworkOrganizationService(Context context){
        organizationApi = RetrofitService.getInstance(context).getOrganizationApi();
        TokenDao = SqlTokenDao.getInstance(context);
    }

    @Override
    public void loadOrganization(final RetrofitCallback<Organization> callback) {
        Token token = TokenDao.get();

        Call<Organization> call = organizationApi.loadOrganization(token.getAccess_token());

        call.enqueue(new Callback<Organization>() {
            @Override
            public void onResponse(Call<Organization> call, Response<Organization> response) {

            }

            @Override
            public void onFailure(Call<Organization> call, Throwable t) {

            }
        });
    }
}
