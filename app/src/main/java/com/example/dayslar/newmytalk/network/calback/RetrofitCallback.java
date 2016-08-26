package com.example.dayslar.newmytalk.network.calback;

import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

public interface RetrofitCallback<T> {

     void onProcess();
     void onSuccess(T object);
     void onFailure(HttpMessage httpMessage);
}
