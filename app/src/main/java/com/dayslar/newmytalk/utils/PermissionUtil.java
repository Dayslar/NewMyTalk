package com.dayslar.newmytalk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    private final List<String> requiredPermissions = new ArrayList<String>(){{
        add(Manifest.permission.READ_PHONE_STATE);
        add(Manifest.permission.CALL_PHONE);
        add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        add(Manifest.permission.RECORD_AUDIO);
        add(Manifest.permission.READ_EXTERNAL_STORAGE);
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
    }};

    public void initRequiredPermission(Activity activity){
        List<String> notActivePermissions = notActivePermissions(activity);

        if (notActivePermissions.size() > 0)
            ActivityCompat.requestPermissions(activity, notActivePermissions.toArray(new String[]{}), 99);
    }

    public List<String> notActivePermissions(Context context){
        List<String> notActivePermissions = new ArrayList<>();
        for(String permission: requiredPermissions){
            if (!checkPermission(context, permission))
                notActivePermissions.add(permission);
        }

        return notActivePermissions;
    }
    public boolean checkPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
