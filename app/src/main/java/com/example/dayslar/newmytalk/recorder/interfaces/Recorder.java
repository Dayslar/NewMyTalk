package com.example.dayslar.newmytalk.recorder.interfaces;

public interface Recorder {

    void startRecord(String outputFile);
    void stopRecord();

    void setAudioSource(int audioSource);
    void setOutputFormat(int outputFormat);
    void setAudioEncoder(int audioEncoder);
    void setAudioChannels(int audioChannels);

}
