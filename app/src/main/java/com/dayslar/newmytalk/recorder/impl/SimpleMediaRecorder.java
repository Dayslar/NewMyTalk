package com.dayslar.newmytalk.recorder.impl;

import android.content.Context;
import android.media.MediaRecorder;

import com.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.dayslar.newmytalk.utils.MyFileUtils;
import com.dayslar.newmytalk.utils.MyLogger;
import com.dayslar.newmytalk.utils.SettingUtil;

import java.io.IOException;

public class SimpleMediaRecorder implements Recorder {

    private static SimpleMediaRecorder instance;

    private MediaRecorder mediaRecorder;
    private SettingUtil settingUtil;
    private boolean isRecording = false;

    private int outputFormat;
    private int audioSource;
    private int audioEncoder;
    private int audioChannels;

    public static SimpleMediaRecorder getInstance(Context context){
        if (instance == null){
           synchronized (SimpleMediaRecorder.class){
               if (instance == null){
                   instance = new SimpleMediaRecorder(context);
               }
           }
        }

        return instance;
    }

    private SimpleMediaRecorder(Context c){
        settingUtil = SettingUtil.getInstance(c);
        initPlayer();
    }

    @Override
    public void startRecord(String outputFile) {
        if (!isRecording){
            try {
                startRecorder(outputFile);
                MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись запущена");

            } catch (IOException e) {
                e.printStackTrace();
                MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Произошла ошибка инициализации рекордера: " + e.getMessage());
            }
        }
    }

    @Override
    public void stopRecord() {
        if (isRecording && mediaRecorder != null) {
            stopRecorder();
        }

        isRecording = false;
        MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись остановлена");
    }


    private void initRecorder(String outputFile) throws IOException {
        MyFileUtils.checkFolder();

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(audioSource);
        mediaRecorder.setOutputFormat(outputFormat);
        mediaRecorder.setAudioEncoder(audioEncoder);
        mediaRecorder.setAudioChannels(audioChannels);
        mediaRecorder.setOutputFile(MyFileUtils.getFolder() + outputFile);

        mediaRecorder.prepare();
    }

    private void startRecorder(String outputFile) throws IOException {
        initRecorder(outputFile);
        mediaRecorder.start();

        isRecording = true;
    }

    private void stopRecorder() {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        } catch (RuntimeException e){
            MyLogger.printDebug(this.getClass(), "Ошибка остановки записи: SimpleMediaRecorder - line 94");
        }

    }

    private void initPlayer(){
        settingUtil.loadSettingForPlayer();
        this.audioSource = settingUtil.getSetting().getAudioSource();
        this.outputFormat = settingUtil.getSetting().getOutputFormat();
        this.audioEncoder = settingUtil.getSetting().getAudioEncoder();
        this.audioChannels = settingUtil.getSetting().getAudioChannels();
    }

}
