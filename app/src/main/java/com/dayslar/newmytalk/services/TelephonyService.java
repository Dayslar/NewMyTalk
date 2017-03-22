package com.dayslar.newmytalk.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.telephony.TelephoneConfig;
import com.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandler;
import com.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;
import com.dayslar.newmytalk.ui.activity.LoginActivity_;

public class TelephonyService extends Service{

    private static final int NOTIFICATION_ID = 222;
    private Notification notification;
    private TelephonyHandler telManager;

    public void onCreate() {
        super.onCreate();

        telManager = SimpleTelephonyHandler.getInstance(this);
        initNotification(createPendingIntent());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, notification);

        if (intent == null)
            return START_STICKY;

        String code = intent.getStringExtra(TelephoneConfig.TELEPHONE_CODE_SERVICE);

        if (code == null)
            return START_STICKY;

        if (code.equals(TelephoneConfig.NEW_OUTGOING_CALL))
            telManager.outgoingCall(intent);

        else if (code.equals(TelephoneConfig.EXTRA_STATE_RUNNING))
            telManager.runningCall(intent);

        else if (code.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK))
            telManager.offhookCall(intent);

        else if (code.equals(TelephoneConfig.EXTRA_STATE_IDLE))
            telManager.idleCall(intent);

        else if (code.equals(TelephoneConfig.EXTRA_STATE_MANAGER)){
            int managerId = intent.getIntExtra("managerId", -1);
            if (managerId != -1)
                telManager.setManagerInfo(managerId);
        }

        else if (code.equals(TelephoneConfig.STATE_EXIT_SERVICE)){
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    private PendingIntent createPendingIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(this, LoginActivity_.class), 0);
    }

       private void initNotification(PendingIntent intent) {
        notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Мои разговоры")
                .setContentText("Служба запущена")
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .build();
    }

}
