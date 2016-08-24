package com.example.dayslar.newmytalk;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.recorder.impl.SimpleMediaRecorder;
import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecorderTest {


    @Test
    public void startRecord() throws InterruptedException {

        Recorder recorder = initPlayer();
        String fileName = Environment.getExternalStorageDirectory() + "/record.mp4";

        recorder.startRecord(fileName);
        recorder.stopRecord();

        fileName = Environment.getExternalStorageDirectory() + "/record1.mp4";
        recorder.startRecord(fileName);
        recorder.stopRecord();

    }

    @NonNull
    private Recorder initPlayer() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        return SimpleMediaRecorder.getInstance(appContext);
    }

}
