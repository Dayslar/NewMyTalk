package com.example.dayslar.newmytalk.ui.fragment;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceFragment;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.receivers.AdminReceiver;
import com.example.dayslar.newmytalk.utils.SettingUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public class SettingBaseFragment extends PreferenceFragment {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SettingUtil settingUtil;

    @AfterViews
    void init(){
        settingUtil = SettingUtil.getInstance(getActivity());

        addPreferencesFromResource(R.xml.preferences_base);
        initListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        super.onPause();
    }


    private void initListener(){
        listener =  new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                checkAdmin(sharedPreferences);
                settingUtil.loadSetting();
            }
        };
    }

    private void checkAdmin(SharedPreferences sharedPreferences) {
        Boolean isAdmin = sharedPreferences.getBoolean(getResources().getString(R.string.chAdminKey), false);

        if (isAdmin) activeAdmin();
        else disableAdmin();
    }

    private void activeAdmin() {
        ComponentName mDeviceAdminSample = new ComponentName(getActivity(), AdminReceiver.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
    }

    private void disableAdmin() {
        ComponentName mDeviceAdminSample = new ComponentName(getActivity(), AdminReceiver.class);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.removeActiveAdmin(mDeviceAdminSample);
    }


}
