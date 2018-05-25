package com.dayslar.newmytalk.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.dayslar.newmytalk.network.service.impl.NetworkRecordService;
import com.dayslar.newmytalk.network.service.impl.NetworkTokenService;
import com.dayslar.newmytalk.network.service.interfaces.RecordService;
import com.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.dayslar.newmytalk.ui.activity.MainActivity_;

import java.util.List;

public class UnloadService extends Service{

    private Notification notification;
    private static final int NOTIFICATION_ID = 876;

    private RecordDao RecordDao;
    private RecordService recordService;
    private TokenDao TokenDao;
    private TokenService tokenService;

    public void onCreate() {
        super.onCreate();

        this.RecordDao = SqlRecordDao.getInstance(this);
        this.TokenDao = SqlTokenDao.getInstance(this);
        this.tokenService = new NetworkTokenService(this);
        this.recordService = new NetworkRecordService(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) initNotificationO(createPendingIntent());
        else initNotification(createPendingIntent());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, notification);

        Token token = TokenDao.get();
        tokenService.loadTokenByRefreshToken(token.getRefresh_token(), new RetrofitCallback<Token>() {
            @Override
            public void onProcess() {
            }

            @Override
            public void onSuccess(Token object) {
                List<Record> records = RecordDao.getRecords();
                for (Record record: records){
                    if (record.getFileName() == null) recordService.sendRecord(record);
                    else recordService.sendRecordAndFile(record);
                    onProcess();
                }
            }

            @Override
            public void onFailure(HttpMessage httpMessage) {
                stopForeground(true);
            }
        });

        stopForeground(true);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    private PendingIntent createPendingIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(this, MainActivity_.class), 0);
    }

    private void initNotification(PendingIntent intent) {
        notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Мои разговоры")
                .setContentText("Идет выгрузка записей")
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void initNotificationO(PendingIntent intent) {
        notification = new Notification.Builder(this, createNotificationChannel())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Мои разговоры")
                .setContentText("Идет выгрузка записей")
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .setCategory(Notification.CATEGORY_SERVICE)
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(){
        String channelId = "my_talk";
        String channelName = "my_talk_call";
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        service.createNotificationChannel(chan);
        return channelId;
    }

}
