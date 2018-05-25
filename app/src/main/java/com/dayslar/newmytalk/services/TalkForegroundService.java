package com.dayslar.newmytalk.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dayslar.newmytalk.receivers.TelephoneReceiver;
import com.dayslar.newmytalk.utils.NotifyManager;

public class TalkForegroundService extends Service {

    private TelephoneReceiver telephoneReceiver;
    private static final int NOTIFICATION_ID = 222;

    @Override
    public void onCreate() {
        super.onCreate();

        telephoneReceiver = new TelephoneReceiver();

        IntentFilter telephoneFilter = new IntentFilter();
        telephoneFilter.addAction("android.intent.action.PHONE_STATE");
        telephoneFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");

        registerReceiver(telephoneReceiver, telephoneFilter);
        startForeground(NOTIFICATION_ID, NotifyManager.getInstance().getNotification(this, "Служба запущена"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet im operation");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(telephoneReceiver);
    }


    public static void start(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(new Intent(context, TalkForegroundService.class));
        }

        else {
            context.startService(new Intent(context, TalkForegroundService.class));
        }
    }
}
