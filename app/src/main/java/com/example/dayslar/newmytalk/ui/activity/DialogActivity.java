package com.example.dayslar.newmytalk.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.TelephonyState;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.impl.SqlTelephonyStateDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TelephonyStateDao;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.dialog_activity)
public class DialogActivity extends AppCompatActivity {

    @ViewById(R.id.fabEndTalk) FloatingActionButton fabEndTalk;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private TelephonyStateDao stateDao;
    private RecordDao recordDao;

    @AfterViews
    public void init(){

        stateDao = SqlTelephonyStateDao.getInstance(this);
        recordDao = SqlRecordDao.getInstance(this);

        initToolbar();
        fabEndTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleTelephonyHandler.endCall();
            }
        });
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

        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(record.getContactName() != null
                ? record.getContactName()
                : "Неизвестный обонент");
        toolbar.setSubtitle(record.getCallPhone());
    }
}
