package com.example.dayslar.newmytalk.recorder.interfaces;

public interface Recorder {

    void startRecord(String outputFile);
    void stopRecord();

    Recorder setAudioSource(int audioSource);
    Recorder setOutputFormat(int outputFormat);
    Recorder setAudioEncoder(int audioEncoder);
    Recorder setAudioChannels(int audioChannels);

}
