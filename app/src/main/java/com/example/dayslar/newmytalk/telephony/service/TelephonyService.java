package com.example.dayslar.newmytalk.telephony.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.main.MainActivity;
import com.example.dayslar.newmytalk.telephony.TelephoneConfig;
import com.example.dayslar.newmytalk.telephony.impl.SimpleTelephonyHandlerImpl;
import com.example.dayslar.newmytalk.telephony.interfaces.TelephonyHandler;

public class TelephonyService extends Service {

    private static final int NOTIFICATION_ID = 222;
    private Notification notification;
    private TelephonyHandler telManager;

    public void onCreate() {
        super.onCreate();

        telManager = SimpleTelephonyHandlerImpl.getInstance(this);
        initNotification(createPendingIntent());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, notification);
        String code = intent.getStringExtra(TelephoneConfig.TELEPHONE_CODE_SERVICE);

        if (code == null)
            return START_STICKY;

        if (code.equals(TelephoneConfig.NEW_OUTGOING_CALL)){
            telManager.outgoingCall(intent);
        }
        else if (code.equals(TelephoneConfig.EXTRA_STATE_RUNNING)){
            telManager.runningCall(intent);
        }

        else if (code.equals(TelephoneConfig.EXTRA_STATE_OFFHOOK)) {
            telManager.offhookCall(intent);
        }

        else if (code.equals(TelephoneConfig.EXTRA_STATE_IDLE)){
            telManager.idleCall(intent);
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    private PendingIntent createPendingIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
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
