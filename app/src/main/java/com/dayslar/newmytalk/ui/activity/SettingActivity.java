package com.dayslar.newmytalk.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.ui.fragment.SettingBaseFragment_;
import com.dayslar.newmytalk.ui.fragment.SettingNetworkFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.preference_activity)
public class SettingActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar) Toolbar toolbar;

    @AfterViews
    void init(){
        initToolbar();
        initSettingFragment();
    }

    private void initToolbar() {
        toolbar.setTitle("Настройки");
        toolbar.setSubtitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initSettingFragment() {
        if (getIntent().getIntExtra(SettingType.TYPE, SettingType.SETTING_BASE) == SettingType.SETTING_NETWORK)
            getFragmentManager().beginTransaction().replace(R.id.content_frame, SettingNetworkFragment_.builder().build()).commit();
        else getFragmentManager().beginTransaction().replace(R.id.content_frame, SettingBaseFragment_.builder().build()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static final class SettingType {
        static final String TYPE = "setting_type";
        static final int SETTING_BASE = 0;
        static final int SETTING_NETWORK = 1;
    }
}
