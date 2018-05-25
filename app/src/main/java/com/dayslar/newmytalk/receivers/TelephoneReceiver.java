package com.dayslar.newmytalk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dayslar.newmytalk.telephony.TelephoneConfig;
import com.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.dayslar.newmytalk.utils.SettingUtil;

public class TelephoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SettingUtil settingUtil = SettingUtil.getInstance(context);
        settingUtil.loadSetting();

        if (settingUtil.getSetting().isRecordingActive()) {
            switch (intent.getAction()) {
                case TelephoneConfig.NEW_OUTGOING_CALL:
                    SimpleTelephonyHandler.getInstance(context).outgoingCall(intent);
                    break;

                case TelephoneConfig.PHONE_STATE:
                    String phoneState = intent.getStringExtra(TelephoneConfig.EXTRA_STATE);

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_RUNNING)){
                        SimpleTelephonyHandler.getInstance(context).runningCall(intent);
                    }

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK)) {
                        SimpleTelephonyHandler.getInstance(context).offhookCall(intent);
                    }

                    if (phoneState.equals(TelephoneConfig.EXTRA_STATE_IDLE)) {
                        SimpleTelephonyHandler.getInstance(context).idleCall(intent);
                    }
                    break;
            }
        }
    }

}
