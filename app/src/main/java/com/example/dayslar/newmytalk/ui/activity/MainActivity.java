package com.example.dayslar.newmytalk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.example.dayslar.newmytalk.network.service.impl.NetworkManagerService;
import com.example.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.example.dayslar.newmytalk.ui.adapter.ManagerAdapter;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.calback.RetrofitCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.main_activity)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.rv) RecyclerView recyclerView;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private ManagerDao managerDao;
    private ManagerService managerService;
    private Snackbar snackbar;

    private Context context;
    private View.OnClickListener managerListener;
    private boolean isRecording;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        this.isRecording = intent.getBooleanExtra("isRecording", false);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Активите не пересоздавалось второй раз");
    }

    @AfterViews
    void init(){
        this.context = this;
        this.managerDao = SqlManagerDao.getInstance(this);
        this.managerService = new NetworkManagerService(this);
        this.managerListener = managerClickListener();
        this.isRecording = getIntent().getBooleanExtra("isRecording", false);

        initRecycleView();
        initToolbar();
        initFab();

    }

    private void getServerMangers() {
        managerService.loadManagers(new RetrofitCallback<List<Manager>>() {
            @Override
            public void onProcess() {
                snackbar = Snackbar.make(fab, "Идет обновление данных...", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }

            @Override
            public void onSuccess(Call<List<Manager>> call, Response<List<Manager>> response) {
                recyclerView.setAdapter(new ManagerAdapter(response.body(), managerListener));
                snackbar.setText("Обновление успешно завершено.").setDuration(3000).show();
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable e) {
                snackbar.setText("Не удалось подключиться к серверу.").setDuration(4000).show();
            }
        });
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServerMangers();
            }
        });
    }

    private void initToolbar() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setSubtitle(R.string.app_name);
        toolbar.setTitle("Ростинг");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.setting:
                        Intent intent = new Intent(context, PinActivity_.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.main_menu);
    }

    private void initRecycleView() {

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(new ManagerAdapter(managerDao.getManagers(), managerListener));
    }

    private View.OnClickListener managerClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) SimpleTelephonyHandler.answerCall(context);
                else Snackbar.make(fab, "Сейчас никто не звонит!", Snackbar.LENGTH_LONG).show();

            }
        };
    }

}
