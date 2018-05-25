package com.dayslar.newmytalk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dayslar.newmytalk.services.TalkForegroundService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TalkForegroundService.start(context);
    }
}
