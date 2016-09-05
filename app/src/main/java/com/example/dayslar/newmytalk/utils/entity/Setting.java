package com.example.dayslar.newmytalk.utils.entity;


import android.media.MediaRecorder;

public class Setting {
    private Boolean recordingActive;
    private Boolean managerActive;
    private Boolean adminActive;
    private Boolean unloadActive;

    private int delay;
    private String serverPort;
    private String serverIp;

    private int outputFormat = MediaRecorder.OutputFormat.THREE_GPP;
    private int audioSource = MediaRecorder.AudioSource.MIC;
    private int audioEncoder = MediaRecorder.AudioEncoder.AAC;
    private int audioChannels = 1;

    public String getServerPort() {
        return serverPort;
    }

    public Setting setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public Boolean isRecordingActive() {
        return recordingActive;
    }

    public Setting setRecordingActive(Boolean recordingActive) {
        this.recordingActive = recordingActive;
        return this;
    }

    public Boolean isManagerActive() {
        return managerActive;
    }

    public Setting setManagerActive(Boolean managerActive) {
        this.managerActive = managerActive;
        return this;
    }

    public Boolean isAdminActive() {
        return adminActive;
    }

    public Setting setAdminActive(Boolean adminActive) {
        this.adminActive = adminActive;
        return this;
    }

    public Boolean isUnloadActive() {
        return unloadActive;
    }

    public Setting setUnloadActive(Boolean unloadActive) {
        this.unloadActive = unloadActive;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public Setting setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public Setting setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public int getOutputFormat() {
        return outputFormat;
    }

    public Setting setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public Setting setAudioSource(int audioSource) {
        this.audioSource = audioSource;
        return this;
    }

    public int getAudioEncoder() {
        return audioEncoder;
    }

    public Setting setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
        return this;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public Setting setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
        return this;
    }
}
