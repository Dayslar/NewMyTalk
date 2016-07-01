package com.example.dayslar.newmytalk.recorder;

import android.media.MediaRecorder;

public interface Recorder {

    void startRecord();
    void stopRecord();

    void setAutioSource(MediaRecorder.AudioSource audioSource);
    void setOutputFormat(MediaRecorder.OutputFormat outputFormat);
    void setOutputFile(String fileName);

}
