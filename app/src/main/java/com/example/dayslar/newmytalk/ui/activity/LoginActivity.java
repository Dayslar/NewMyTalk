package com.example.dayslar.newmytalk.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDAO;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.example.dayslar.newmytalk.network.service.impl.NetworkTokenService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewById(R.id.et_username) EditText etUsername;
    @ViewById(R.id.et_password) EditText etPassword;
    @ViewById(R.id.btnLogin) Button btGo;
    @ViewById(R.id.cv) CardView cv;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TokenService tokenService;
    private TokenDAO tokenDAO;
    private Snackbar snackBar;

    @AfterViews
    void init() {

        tokenService = new NetworkTokenService(this);
        tokenDAO = SqlTokenDao.getInstance(this);

        initToolbar();

        fab.setOnClickListener(this);
        btGo.setOnClickListener(this);

        checkActiveAccount();
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
        toolbar.inflateMenu(R.menu.login_menu);
        toolbar.setOnMenuItemClickListener(initToolbarListener(this));
    }

    private void login(String username, String password) {

        snackBar = Snackbar.make(fab, "", Snackbar.LENGTH_SHORT);

        if (checkUserData(username)){
            snackBar.setText(getString(R.string.loginCheckUsernameError)).setDuration(Snackbar.LENGTH_LONG).show();
            return;
        }

        else if (checkUserData(password)){
            snackBar.setText(R.string.loginCheckPasswordError).setDuration(Snackbar.LENGTH_LONG).show();
            return;
        }

        tokenService.loadToken(username, password, new RetrofitCallback<Token>() {
            @Override
            public void onProcess() {
                enabledButtons(false);
                snackBar.setText(R.string.loginServerProcess).setDuration(Snackbar.LENGTH_INDEFINITE).show();
            }

            @Override
            public void onSuccess(Token token) {
                snackBar.dismiss();
                enabledButtons(true);
                startMainActivity();
            }

            @Override
            public void onFailure(HttpMessage httpMessage) {
                snackBar.setText(httpMessage.getMessage()).setDuration(Snackbar.LENGTH_LONG).show();
                enabledButtons(true);
            }
        });

    }

    private boolean checkUserData(String value) {
        return (value.isEmpty() || value.trim().isEmpty());
    }

    private void enabledButtons(boolean value) {
        btGo.setEnabled(value);
        fab.setEnabled(value);
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity_.class));
    }

    private void checkActiveAccount(){
        if (tokenDAO.get() != null)
            startMainActivity();
    }

    private Toolbar.OnMenuItemClickListener initToolbarListener(final Context context){
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent settingInt = new Intent(context, SettingActivity_.class);
                settingInt.putExtra(SettingActivity.SettingType.TYPE, SettingActivity.SettingType.SETTING_NETWORK);
                startActivity(settingInt);

                return true;
            }
        };
    }
}
