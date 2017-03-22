package com.dayslar.newmytalk.network.calback;

import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public interface RetrofitCallback<T> {

     void onProcess();
     void onSuccess(T object);
     void onFailure(HttpMessage httpMessage);
}
