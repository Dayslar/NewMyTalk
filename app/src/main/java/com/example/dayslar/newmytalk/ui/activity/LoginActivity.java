package com.example.dayslar.newmytalk.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.network.service.impl.NetworkTokenService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.utils.calback.RetrofitCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Response;


@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewById(R.id.et_username)EditText etUsername;
    @ViewById(R.id.et_password)EditText etPassword;
    @ViewById(R.id.bt_go)Button btGo;
    @ViewById(R.id.cv)CardView cv;
    @ViewById(R.id.fab)FloatingActionButton fab;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TokenService tokenService;
    private Snackbar snackbar;

    @AfterViews
    void init(){

        tokenService = new NetworkTokenService(this);

        initToolbar();

        fab.setOnClickListener(this);
        btGo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startRegisterActivityAnimation();
                break;
            case R.id.bt_go:
                login(etUsername.getText().toString(), etPassword.getText().toString());
                break;
        }
    }

    private void startRegisterActivityAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivity(new Intent(this, RegisterActivity_.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity_.class));
        }
    }

    private void initToolbar() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Вход");
        toolbar.setSubtitle(R.string.app_name);
    }

    private void login(String username, String password){

        snackbar = Snackbar.make(fab, "Отправка данных на сервер", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        enabledButtons(false);

        tokenService.loadToken(username, password, new RetrofitCallback<Token>() {
            @Override
            public void onProcess() {

            }

            @Override
            public void onSuccess(Call<Token> call, Response<Token> response) {
                snackbar.setText("Обновление завершено").setDuration(2000).show();
                goToMailActivity();

                enabledButtons(true);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable e) {
                snackbar.setText("Ошибка подколючения к серверу, " +
                        "проверьте ваши сетевые настройи и попробуйте сново").setDuration(2000).show();

                enabledButtons(true);
            }
        });


    }

    private void enabledButtons(boolean value){
        btGo.setEnabled(value);
        fab.setEnabled(value);
    }

    private void goToMailActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);

            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }

        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent mainIntent = new Intent(this, MainActivity_.class);
        startActivity(mainIntent, oc2.toBundle());
    }
}
