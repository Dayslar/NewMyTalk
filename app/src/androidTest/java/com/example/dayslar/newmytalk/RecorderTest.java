package com.example.dayslar.newmytalk;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.recorder.impl.MediaRecorderImpl;
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
        Recorder recorder = MediaRecorderImpl.getInstance();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioChannels(1);
        return recorder;
    }

}
