package com.dayslar.newmytalk.network.utils.http.code;

import com.dayslar.newmytalk.network.utils.http.code.impls.Http201Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http202Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http204Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http400Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http401Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http403Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http404Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http405Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http406Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http500Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http501Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http502Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http503Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.Http504Message;
import com.dayslar.newmytalk.network.utils.http.code.impls.HttpUnsupportedMessage;
import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public class HttpMessageSelector {

    private static HttpMessageSelector instance;

    private HttpMessageSelector(){

    }

    public static HttpMessageSelector getInstance(){
        if (instance == null){
            synchronized (HttpMessageSelector.class){
                if (instance == null)
                    instance = new HttpMessageSelector();
            }
        }

        return instance;
    }

    public HttpMessage getMessage(int code){
        switch (code){
            case 201: return new Http201Message();
            case 202: return new Http202Message();
            case 204: return new Http204Message();
            case 400: return new Http400Message();
            case 401: return new Http401Message();
            case 403: return new Http403Message();
            case 404: return new Http404Message();
            case 405: return new Http405Message();
            case 406: return new Http406Message();
            case 500: return new Http500Message();
            case 501: return new Http501Message();
            case 502: return new Http502Message();
            case 503: return new Http503Message();
            case 504: return new Http504Message();
            default: return new HttpUnsupportedMessage();
        }
    }

}
