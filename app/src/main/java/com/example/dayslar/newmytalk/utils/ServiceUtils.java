package com.example.dayslar.newmytalk.utils;


import android.content.Context;
import android.content.Intent;

import com.example.dayslar.newmytalk.services.TelephonyService;
import com.example.dayslar.newmytalk.telephony.TelephoneConfig;

public class ServiceUtils {

    public static void  sendTelephoneService(Context context, Intent intent, String code) {
        intent.setClass(context, TelephonyService.class);
        intent.putExtra(TelephoneConfig.TELEPHONE_CODE_SERVICE, code);
        context.startService(intent);
    }

    public static void sendTelephoneService(Context context, String code) {
        Intent intent = new Intent();
        intent.setClass(context, TelephonyService.class);
        intent.putExtra(TelephoneConfig.TELEPHONE_CODE_SERVICE, code);
        context.startService(intent);
    }
}
