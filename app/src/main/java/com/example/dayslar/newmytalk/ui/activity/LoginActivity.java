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
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
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

    @ViewById(R.id.et_username) EditText etUsername;
    @ViewById(R.id.et_password) EditText etPassword;
    @ViewById(R.id.btnLogin) Button btGo;
    @ViewById(R.id.cv) CardView cv;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TokenService tokenService;
    private TokenDao tokenDao;
    private Snackbar snackbar;

    @AfterViews
    void init() {

        tokenService = new NetworkTokenService(this);
        tokenDao = SqlTokenDao.getInstance(this);

        initToolbar();
        initSnackbar();

        fab.setOnClickListener(this);
        btGo.setOnClickListener(this);

        checkActiveAccount();
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(cv, "", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startRegisterActivityAnimation();
                break;
            case R.id.btnLogin:
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
        toolbar.setTitle(R.string.loginTvLogin);
        toolbar.setSubtitle(R.string.app_name);
    }

    private void login(String username, String password) {

        if (checkUserData(username)){
            Snackbar.make(cv, getString(R.string.loginCheckUsernameError), Snackbar.LENGTH_LONG).show();
            return;
        }

        else if (checkUserData(password)){
            Snackbar.make(cv, R.string.loginCheckPasswordError, Snackbar.LENGTH_LONG).show();
            return;
        }

        tokenService.loadToken(username, password, new RetrofitCallback<Token>() {
            @Override
            public void onProcess() {
                enabledButtons(false);
                snackbar.setText(R.string.loginServerProcess).setDuration(Snackbar.LENGTH_INDEFINITE).show();
            }

            @Override
            public void onSuccess(Call<Token> call, Response<Token> response) {
                snackbar.setText(R.string.loginServerSuccess).setDuration(2000).show();
                enabledButtons(true);
                startMailActivityAnimation();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable e) {
                snackbar.setText(R.string.loginServerFailure).setDuration(2000).show();
                enabledButtons(true);
            }
        });

    }

    private boolean checkUserData(String value) {
        return  (value.isEmpty() || value.trim().isEmpty());
    }

    private void enabledButtons(boolean value) {
        btGo.setEnabled(value);
        fab.setEnabled(value);
    }

    private void startMailActivityAnimation() {
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

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity_.class));
    }

    private void checkActiveAccount(){
        if (tokenDao.get() != null)
            startMainActivity();
    }
}
