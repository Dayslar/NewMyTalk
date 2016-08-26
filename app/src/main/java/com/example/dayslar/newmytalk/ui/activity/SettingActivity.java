package com.example.dayslar.newmytalk.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.ui.fragment.SettingBaseFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.preference_activity)
public class SettingActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar) Toolbar toolbar;

    @AfterViews
    void init(){
        initToolbar();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, SettingBaseFragment_.builder().build()).commit();
    }

    private void initToolbar() {
        toolbar.setTitle("Настройки");
        toolbar.setSubtitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
