package com.example.dayslar.newmytalk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.utils.MyLogger;

public class TelephoneReceiver extends BroadcastReceiver {

    private static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    private static final String PHONE_STATE = "android.intent.action.PHONE_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case NEW_OUTGOING_CALL:
                MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Мы звоним");
                break;

            case PHONE_STATE:
                String phoneState = intent.getStringExtra(android.telephony.TelephonyManager.EXTRA_STATE);

                if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_RINGING)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Получаем звонок");
                }

                if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Отвечаем на звонок");
                }

                if (phoneState.equals(android.telephony.TelephonyManager.EXTRA_STATE_IDLE)) {
                    MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Ложим трубку");
                }

                break;
        }
    }
}
