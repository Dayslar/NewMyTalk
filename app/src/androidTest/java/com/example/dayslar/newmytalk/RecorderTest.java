package com.example.dayslar.newmytalk;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.recorder.Recorder;
import com.example.dayslar.newmytalk.recorder.MediaRecorderImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecorderTest {


    @Test
    public void startRecord(){

        String fileName = Environment.getExternalStorageDirectory() + "/record.mp4";

        Recorder recorder = MediaRecorderImpl.getInstance();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioChannels(1);
        recorder.startRecord(fileName);
        recorder.stopRecord();


    }

}
