package com.example.dayslar.newmytalk.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.entity.Token;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;
import com.example.dayslar.newmytalk.db.interfaces.dao.TokenDao;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;
import com.example.dayslar.newmytalk.network.service.impl.NetworkRecordService;
import com.example.dayslar.newmytalk.network.service.impl.NetworkTokenService;
import com.example.dayslar.newmytalk.network.service.interfaces.RecordService;
import com.example.dayslar.newmytalk.network.service.interfaces.TokenService;
import com.example.dayslar.newmytalk.network.utils.http.code.interfaces.HttpMessage;
import com.example.dayslar.newmytalk.ui.activity.MainActivity_;
import com.example.dayslar.newmytalk.utils.MyLogger;

import java.util.List;

public class UnloadService extends Service{

    private Notification notification;
    private static final int NOTIFICATION_ID = 876;

    private RecordDao recordDao;
    private RecordService recordService;
    private TokenDao tokenDao;
    private TokenService tokenService;

    public void onCreate() {
        super.onCreate();

        this.recordDao = SqlRecordDao.getInstance(this);
        this.tokenDao = SqlTokenDao.getInstance(this);
        this.tokenService = new NetworkTokenService(this);
        this.recordService = new NetworkRecordService(this);

        initNotification(createPendingIntent());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, notification);

        Token token = tokenDao.get();
        tokenService.loadTokenByRefreshToken(token.getRefresh_token(), new RetrofitCallback<Token>() {
            @Override
            public void onProcess() {
                MyLogger.printDebug(UnloadService.class, "Запись выгружена");
            }

            @Override
            public void onSuccess(Token object) {
                List<Record> records = recordDao.getRecords();
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

}
