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
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.TelephonyState;
import com.example.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.example.dayslar.newmytalk.network.service.impl.NetworkManagerService;
import com.example.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.example.dayslar.newmytalk.telephony.TelephoneConfig;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.example.dayslar.newmytalk.ui.adapter.AdapterCallback;
import com.example.dayslar.newmytalk.ui.adapter.ManagerAdapter;
import com.example.dayslar.newmytalk.ui.decorator.GridSpacingDecorator;
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
    @ViewById(R.id.callLayout) CardView cardView;

    @ViewById(R.id.tvEndCall) TextView tvEndCall;
    @ViewById(R.id.contactNumber) TextView contactNumber;

    private ManagerDao managerDao;
    private TelephonyStateDao stateDao;
    private RecordDao recordDao;
    private ManagerService managerService;
    private Snackbar snackbar;

    private Context context;
    private TelephonyState state;

    @AfterViews
    void init() {
        this.context = this;
        this.managerDao = SqlManagerDao.getInstance(this);
        this.recordDao = SqlRecordDao.getInstance(this);
        this.managerService = new NetworkManagerService(this);
        this.stateDao = SqlTelephonyStateDao.getInstance(this);

        initTelephonyState();
        initViews();
        initCallState();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initTelephonyState();
        initCallState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initTelephonyState();
        initCallState();

        screenLockOff();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    public AdapterCallback<Manager> getManagerAdapterCallback(){
        return new AdapterCallback<Manager>() {
            @Override
            public void onClick(Manager manager) {
                if (state.getState().equals(TelephonyState.State.RINGING)) {
                    SimpleTelephonyHandler.answerCall(context);

                    Intent intent = new Intent();
                    intent.putExtra("managerId", manager.getId());

                    ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.EXTRA_STATE_MANAGER);
                } else Snackbar.make(fab, "Сейчас никто не звонит!", Snackbar.LENGTH_LONG).show();
            }
        };
    }

    //init views
    private void initViews() {
        initRecycleView();
        initToolbar();
        initFab();
        initPermissions();
        initTvEndCall();
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
        recyclerView.addItemDecoration(new GridSpacingDecorator(-5));
        recyclerView.setAdapter(new ManagerAdapter(managerDao.getManagers(), getManagerAdapterCallback()));
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServerMangers();
            }
        });
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

    private void initTelephonyState() {
        state = stateDao.getTelephonyState();
    }

    private void initCallState() {
        switch (state.getState()){
            case TelephonyState.State.RINGING:
                Record record = recordDao.get(state.getRecordId());
                contactNumber.setText(record.getCallPhone());
                cardView.setVisibility(View.VISIBLE);
                break;

            case TelephonyState.State.RECORDING:
                MyLogger.printDebug(getClass(), "Попали в ветку recording");
                startActivity(new Intent(this, DialogActivity_.class));
                break;

            case TelephonyState.State.NOT_RINGING:
                cardView.setVisibility(View.GONE);
                break;

            default:
                cardView.setVisibility(View.GONE);
                break;
        }
    }

    private void initTvEndCall() {
        tvEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleTelephonyHandler.endCall();
            }
        });
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

    private void screenLockOff() {
        Window wind = getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private AlertDialog logoutDialog(){
        return new AlertDialog.Builder(this, R.style.AlertDialogCustomLight)
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.btnDialogYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqlTokenDao.getInstance(context).delete();
                        SqlManagerDao.getInstance(context).deleteAll();
                        SqlRecordDao.getInstance(context).deleteAll();

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putBoolean(getString(R.string.chActiveRecordKey), false);
                        editor.apply();

                        ServiceUtils.sendTelephoneService(context, TelephoneConfig.STATE_EXIT_SERVICE);

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

}
