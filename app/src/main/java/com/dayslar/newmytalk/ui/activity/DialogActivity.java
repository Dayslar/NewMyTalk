package com.dayslar.newmytalk.ui.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.entity.TelephonyState;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.dayslar.newmytalk.utils.ActivityUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.dialog_activity)
public class DialogActivity extends AppCompatActivity {

    @ViewById(R.id.fabEndTalk) FloatingActionButton fabEndTalk;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TelephonyStateDao stateDao;
    private RecordDao recordDao;
    private Context context;

    @AfterViews
    public void init() {
        context = this;
        stateDao = SqlTelephonyStateDao.getInstance(this);
        recordDao = SqlRecordDao.getInstance(this);

        initToolbar();
        initFab();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityUtils.screenLockOff(this);
    }

    @Override
    public void onBackPressed() {
        switch (stateDao.getTelephonyState().getState()) {
            case TelephonyState.State.RECORDING:
                Toast.makeText(this, "Нельзя выйти пока идет разговор", Toast.LENGTH_LONG).show();
                break;
            case TelephonyState.State.NOT_RINGING:
                super.onBackPressed();
                break;
        }
    }

    private void initToolbar() {
        TelephonyState telephonyState = stateDao.getTelephonyState();
        Record record = recordDao.get(telephonyState.getRecordId());

        initToolbarData(record);
    }

    private void initToolbarData(Record record) {
        if (record == null) {
            toolbar.setTitle("Неизвестный абонент");
            toolbar.setSubtitle("Скрытый номер");
        }
        else {
            toolbar.setTitle(record.getContactName() == null
                    ? "Неизвестный абонент"
                    : record.getContactName());
            toolbar.setSubtitle(record.getCallPhone());
        }
        toolbar.setLogo(R.mipmap.ic_launcher);
    }

    private void initFab() {
        fabEndTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleTelephonyHandler.endCall(context);
            }
        });
    }
}
