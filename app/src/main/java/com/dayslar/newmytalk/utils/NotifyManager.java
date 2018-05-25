package com.dayslar.newmytalk.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.ui.activity.MainActivity_;

public class NotifyManager {
    private static NotifyManager instance;

    public static NotifyManager getInstance(){
        if (instance == null) {
            synchronized (NotifyManager.class) {
                if (instance == null)
                    instance = new NotifyManager();
            }
        }
        return instance;
    }

    public Notification getNotification(Context context, String message){
        PendingIntent intent = createPendingIntent(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return getNotificationO(context, intent, message);
        else return getNotificationBeforeO(context, intent, message);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private Notification getNotificationO(Context context, PendingIntent intent, String message){
        return new Notification.Builder(context, createNotificationChannel(context))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Мои разговоры")
                .setContentText(message)
                .setContentIntent(intent)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setAutoCancel(false)
                .build();
    }

    private Notification getNotificationBeforeO(Context context, PendingIntent intent, String message){
        return new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Мои разговоры")
                .setContentText("Служба запущена")
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(Context context){
        String channelId = "my_talk";
        String channelName = "my_talk_call";

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(context.getString(R.string.app_name));
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.createNotificationChannel(channel);

        return channelId;
    }
    private PendingIntent createPendingIntent(Context context) {
        return PendingIntent.getActivity(context, 0, new Intent(context, MainActivity_.class), 0);
    }
}
