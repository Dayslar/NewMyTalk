package com.dayslar.newmytalk.network.service.interfaces;

import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;

public interface TokenService {

    void loadToken(String username, String password, RetrofitCallback<Token> callback);
    void loadTokenByRefreshToken(String refreshToken, RetrofitCallback<Token> callback);
}
