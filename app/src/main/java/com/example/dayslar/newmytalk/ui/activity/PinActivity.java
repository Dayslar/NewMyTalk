package com.example.dayslar.newmytalk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.example.dayslar.newmytalk.network.service.RetrofitService;
import com.example.dayslar.newmytalk.network.utils.http.code.impls.Http503Message;
import com.example.dayslar.newmytalk.utils.entity.LockKey;
import com.example.dayslar.newmytalk.utils.MyLogger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.pin_layout)
public class PinActivity extends AppCompatActivity {

    @ViewById(R.id.pin_lock_view) PinLockView pinLockView;
    @ViewById(R.id.indicator_dots) IndicatorDots indicatorDots;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TokenDao TokenDao;
    private Context context;

    @AfterViews
    void init(){

        this.context = this;
        this.TokenDao = SqlTokenDao.getInstance(context);

        initializeToolbar();
        initializePinView();

    }

    private void initializePinView() {

        pinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        indicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(initPinLockListener());

        pinLockView.setPinLength(4);
        pinLockView.setTextColor(getResources().getColor(R.color.white));
    }

    private PinLockListener initPinLockListener(){
        return new PinLockListener() {

            @Override
            public void onComplete(final String pin) {

                MyLogger.printDebug(this.getClass(), pin);

                Snackbar.make(pinLockView, "Началась проверка данных ожидайте", Snackbar.LENGTH_INDEFINITE);
                Token token = TokenDao.get();

                Call<LockKey> call = RetrofitService.getInstance(context).getLockKeyApi().getKey(token.getAccess_token());

                call.enqueue(new Callback<LockKey>() {
                    @Override
                    public void onResponse(Call<LockKey> call, Response<LockKey> response) {
                        LockKey lockKey = response.body();

                        if (lockKey != null){
                            if (lockKey.getKey() == Integer.parseInt(pin))
                                startActivity(new Intent(context, SettingActivity_.class));

                            else {
                                Snackbar.make(pinLockView, "Введен неверный пароль", Snackbar.LENGTH_LONG).show();
                                pinLockView.resetPinLockView();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<LockKey> call, Throwable t) {
                        Snackbar.make(pinLockView ,new Http503Message().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onEmpty() {

            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {

            }
        };
    }

    private void initializeToolbar() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Ввод пароля");
        toolbar.setSubtitle(R.string.app_name);
    }
}
