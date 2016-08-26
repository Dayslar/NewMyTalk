package com.example.dayslar.newmytalk.network.utils.http.code.impls;

import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public class Http503Message implements HttpMessage{

    @Override
    public String getMessage() {
        return "Не удалось подключитсья к серверу, проверьте ваши сетевые настройки и повторити попытку сново.";
    }
}

