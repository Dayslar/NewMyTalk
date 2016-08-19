package com.example.dayslar.newmytalk.utils.calback;

import retrofit2.Call;
import retrofit2.Response;

public interface RetrofitCallback<T> {

     void onProcess();
     void onSuccess(Call<T> call, Response<T> response);
     void onFailure(Call<T> call, Throwable e);
}
