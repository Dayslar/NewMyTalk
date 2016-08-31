package com.example.dayslar.newmytalk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.telephony.TelephoneConfig;
import com.example.dayslar.newmytalk.utils.ServiceUtils;
import com.example.dayslar.newmytalk.utils.SettingUtil;

public class TelephoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SettingUtil settingUtil = SettingUtil.getInstance(context);
        settingUtil.loadSetting();

        if (settingUtil.getSetting().isRecordingActive()) {
            switch (intent.getAction()) {
                case TelephoneConfig.NEW_OUTGOING_CALL:
                    ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.NEW_OUTGOING_CALL);
                    break;

                case TelephoneConfig.PHONE_STATE:
                    String phoneState = intent.getStringExtra(TelephoneConfig.EXTRA_STATE);

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_RUNNING))
                        ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.EXTRA_STATE_RUNNING);

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK))
                        ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.EXTRA_STATE_OFFHOOK);

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_IDLE))
                        ServiceUtils.sendTelephoneService(context, intent, TelephoneConfig.EXTRA_STATE_IDLE);

                    break;
            }
        }
    }

}
