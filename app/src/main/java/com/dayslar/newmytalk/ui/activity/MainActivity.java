package com.dayslar.newmytalk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.entity.TelephonyState;
import com.dayslar.newmytalk.db.impl.SqlManagerDao;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.dayslar.newmytalk.db.interfaces.dao.ManagerDao;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.dayslar.newmytalk.network.service.impl.NetworkManagerService;
import com.dayslar.newmytalk.network.service.interfaces.ManagerService;
import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.dayslar.newmytalk.services.TalkForegroundService;
import com.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.dayslar.newmytalk.ui.adapter.AdapterCallback;
import com.dayslar.newmytalk.ui.adapter.ManagerAdapter;
import com.dayslar.newmytalk.ui.decorator.GridSpacingDecorator;
import com.dayslar.newmytalk.utils.ActivityUtils;
import com.dayslar.newmytalk.utils.PermissionUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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
    private PermissionUtil permissionUtil;

    private Context context;
    private TelephonyState state;

    @AfterViews
    void init() {
        context = this;
        managerDao = SqlManagerDao.getInstance(this);
        recordDao = SqlRecordDao.getInstance(this);
        managerService = new NetworkManagerService(this);
        stateDao = SqlTelephonyStateDao.getInstance(this);
        permissionUtil = new PermissionUtil();

        initViews();
        initCallState();
        TalkForegroundService.start(this);
        initNotificationDialer();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initCallState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initCallState();
        ActivityUtils.screenLockOff(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public AdapterCallback<Manager> getManagerAdapterCallback(){
        return new AdapterCallback<Manager>() {
            @Override
            public void onClick(Manager manager) {
                if (state.getState().equals(TelephonyState.State.RINGING)) {
                    SimpleTelephonyHandler.answerCall(context);

                    Record record = recordDao.last();
                    if (record != null){
                        record.setManager(manager);
                        recordDao.update(record);
                    }
                } else Snackbar.make(fab, "Сейчас никто не звонит!", Snackbar.LENGTH_LONG).show();
            }
        };
    }


    private void initViews() {
        initRecycleView();
        initToolbar();
        initFab();
        permissionUtil.initRequiredPermission(this);
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
    private void initNotificationDialer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            if (Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) { }
            else
            {
                Intent i = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }

    private void initCallState() {
        state = stateDao.getTelephonyState();
        switch (state.getState()){
            case TelephonyState.State.RINGING:
                Record record = recordDao.last();
                contactNumber.setText(record.getCallPhone());
                cardView.setVisibility(View.VISIBLE);
                break;

            case TelephonyState.State.RECORDING:
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
                SimpleTelephonyHandler.endCall(context);
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
                snackbar.setText(httpMessage.getMessage()).setDuration(3000).show();
            }
        });
    }
}
