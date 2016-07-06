package com.example.dayslar.newmytalk.telephony.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.telephony.TelephoneConfig;
import com.example.dayslar.newmytalk.telephony.service.TelephonyService;

public class TelephoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case TelephoneConfig.NEW_OUTGOING_CALL:
                sendService(context, intent, TelephoneConfig.NEW_OUTGOING_CALL);
                break;

            case TelephoneConfig.PHONE_STATE:
                String phoneState = intent.getStringExtra(TelephoneConfig.EXTRA_STATE);

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_RUNNING)) {
                    sendService(context, intent, TelephoneConfig.EXTRA_STATE_RUNNING);
                }

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK)) {
                    sendService(context, intent, TelephoneConfig.EXTRA_STATE_OFFHOOK);
                }

                if (phoneState.equals(TelephoneConfig.EXTRA_STATE_IDLE)) {
                    sendService(context, intent, TelephoneConfig.EXTRA_STATE_IDLE);
                }

                break;
        }
    }

    private void sendService(Context context, Intent intent, String code) {
        intent.setClass(context, TelephonyService.class);
        intent.putExtra(TelephoneConfig.TELEPHONE_CODE_SERVICE, code);
        context.startService(intent);
    }
}
