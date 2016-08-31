package com.example.dayslar.newmytalk.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDAO;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.example.dayslar.newmytalk.network.service.impl.NetworkManagerService;
import com.example.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.example.dayslar.newmytalk.services.UnloadService;
import com.example.dayslar.newmytalk.telephony.TelephoneConfig;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.example.dayslar.newmytalk.ui.adapter.AdapterCallback;
import com.example.dayslar.newmytalk.ui.adapter.ManagerAdapter;
import com.example.dayslar.newmytalk.utils.MyLogger;
import com.example.dayslar.newmytalk.utils.ServiceUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.main_activity)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.rv) RecyclerView recyclerView;
    @ViewById(R.id.fab) FloatingActionButton fab;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private ManagerDAO managerDao;
    private ManagerService managerService;
    private Snackbar snackbar;

    private Context context;
    private boolean isRecording;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        this.isRecording = intent.getBooleanExtra("isRecording", false);
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Активите не пересоздавалось второй раз");
    }

    @AfterViews
    void init() {
        this.context = this;
        this.managerDao = SqlManagerDao.getInstance(this);
        this.managerService = new NetworkManagerService(this);
        this.isRecording = getIntent().getBooleanExtra("isRecording", false);

        initRecycleView();
        initToolbar();
        initFab();
        initPermissions();


    }

    @Override
    public void onBackPressed() {
        // TODO: 29.08.2016  
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isActivePermission = true;
        if (requestCode == 99) {

            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED)
                    isActivePermission = false;
            }

            if (!isActivePermission) {
                Toast.makeText(this, "Для корректной работы приложения неоходимы все разрешения!", Toast.LENGTH_SHORT).show();
                initPermissions();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initPermissions() {

        ArrayList<String> permissionsList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED)
            permissionsList.add(Manifest.permission.CALL_PHONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED)
            permissionsList.add(Manifest.permission.READ_PHONE_STATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED)
            permissionsList.add(Manifest.permission.RECORD_AUDIO);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)
            permissionsList.add(Manifest.permission.READ_CONTACTS);


        if (permissionsList.size() > 0)
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[]{}), 99);
    }

    private void getServerMangers() {
        managerService.loadManagers(new RetrofitCallback<List<Manager>>() {
            @Override
            public void onProcess() {
                snackbar = Snackbar.make(fab, "Идет обновление данных...", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }

            @Override
            public void onSuccess(List<Manager> managers) {
                recyclerView.setAdapter(new ManagerAdapter(managers, getManagerAdapterCallback()));
                snackbar.setText("Обновление успешно завершено.").setDuration(3000).show();
            }

            @Override
            public void onFailure(HttpMessage httpMessage) {
                snackbar.setText(httpMessage.getMessage()).setDuration(4000).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(this, UnloadService.class));

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

                    case R.id.account_logout:
                        logoutDialog().show();
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
        recyclerView.setAdapter(new ManagerAdapter(managerDao.getManagers(), getManagerAdapterCallback()));
    }

    private AlertDialog logoutDialog(){
        return new AlertDialog.Builder(this, R.style.AlertDialogCustomLight)
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.btnDialogYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqlTokenDao.getInstance(context).delete();
                        SqlManagerDao.getInstance(context).deleteAll();

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putBoolean(getString(R.string.chActiveRecordKey), false);
                        editor.apply();

                        finish();

                    }
                })
                .setNegativeButton(getString(R.string.btnDialogNo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
    }

    public AdapterCallback<Manager> getManagerAdapterCallback(){
        return new AdapterCallback<Manager>() {
            @Override
            public void onClick(Manager manager) {
                if (isRecording) {
                    SimpleTelephonyHandler.answerCall(context);

                    Intent intent = new Intent();
                    intent.putExtra("managerId", manager.getId());

                    ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.EXTRA_STATE_MANAGER);
                } else Snackbar.make(fab, "Сейчас никто не звонит!", Snackbar.LENGTH_LONG).show();
            }
        };
    }

}
