package com.dayslar.newmytalk.network.utils.http.code.impls;

import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public class Http400Message implements HttpMessage {

    @Override
    public String getMessage() {
        return "Неверный запрос";
    }
}
