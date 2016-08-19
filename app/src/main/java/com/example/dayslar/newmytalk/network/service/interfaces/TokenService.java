package com.example.dayslar.newmytalk.network.service.interfaces;

import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.utils.calback.RetrofitCallback;

public interface TokenService {

    void loadToken(String username, String password, RetrofitCallback<Token> callback);
}
