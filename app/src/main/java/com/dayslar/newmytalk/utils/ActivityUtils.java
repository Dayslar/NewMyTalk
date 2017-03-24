package com.dayslar.newmytalk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class ActivityUtils {

    public static void startActivityUseHandler(final Context context, final Class<? extends Activity> activity, int delay){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               initActivity(context, activity);
            }
        }, delay);
    }

    public static void startActivity(Context context, Class<? extends Activity> activity){
       initActivity(context, activity);
    }

    private static void initActivity(final Context context, final Class<? extends Activity> activity){
        Intent activityIntent = new Intent(context, activity);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(activityIntent);
    }

    public static void screenLockOff(Activity activity) {
        Window wind = activity.getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}
