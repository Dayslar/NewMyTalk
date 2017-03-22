package com.dayslar.newmytalk.ui.fragment;

import android.content.res.Configuration;
import android.preference.PreferenceFragment;

import com.dayslar.newmytalk.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public class SettingNetworkFragment extends PreferenceFragment {

    @AfterViews
    void init(){
        addPreferencesFromResource(R.xml.preferences_network);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
