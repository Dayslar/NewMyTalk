package com.dayslar.newmytalk.utils;

import android.os.Environment;

import java.io.File;

public class MyFileUtils {


    public static void checkFolder(){
        File folder = new File(getFolder());

        if (!folder.exists())
            folder.mkdirs();
    }

    public static String getFolder(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.MyRecord/";
    }
}
