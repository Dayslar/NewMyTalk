package com.dayslar.newmytalk.network.utils.http.code.impls;

import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public class Http402Message implements HttpMessage {

    @Override
    public String getMessage() {
        return "Это услуга является платной, для дальнейшего использования внесите оплату через web приложение";
    }
}
