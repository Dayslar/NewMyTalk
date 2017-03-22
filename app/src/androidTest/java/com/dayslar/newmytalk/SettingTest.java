package com.dayslar.newmytalk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dayslar.newmytalk.utils.SettingUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SettingTest {


    @Test
    public void startRecord() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SettingUtil settingUtil = SettingUtil.getInstance(appContext);

        settingUtil.loadSettingForPlayer();

    }


}
