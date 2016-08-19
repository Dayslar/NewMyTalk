package com.example.dayslar.newmytalk.recorder.impl;

import android.media.MediaRecorder;
import android.os.Environment;

import com.example.dayslar.newmytalk.recorder.interfaces.Recorder;
import com.example.dayslar.newmytalk.utils.MyLogger;

import java.io.File;
import java.io.IOException;

public class SimpleMediaRecorder implements Recorder {

    private static SimpleMediaRecorder instance;

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    private int outputFormat;
    private int audioSource;
    private int audioEncoder;
    private int audioChannels;

    public static SimpleMediaRecorder getInstance(){
        if (instance == null){
           synchronized (SimpleMediaRecorder.class){
               if (instance == null){
                   instance = new SimpleMediaRecorder();
               }
           }
        }

        return instance;
    }

    private SimpleMediaRecorder(){
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

    @Override
    public Recorder setAudioSource(int audioSource) {
        this.audioSource = audioSource;
        return this;
    }

    @Override
    public Recorder setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    @Override
    public Recorder setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
        return this;
    }

    @Override
    public Recorder setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
        return this;
    }

    private void initRecorder(String outputFile) throws IOException {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(audioSource);
        mediaRecorder.setOutputFormat(outputFormat);
        mediaRecorder.setAudioEncoder(audioEncoder);
        mediaRecorder.setAudioChannels(audioChannels);
        mediaRecorder.setOutputFile(getFolder() + outputFile);

        mediaRecorder.prepare();
    }

    private void startRecorder(String outputFile) throws IOException {
        initRecorder(outputFile);
        mediaRecorder.start();

        isRecording = true;
    }

    private void stopRecorder() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void initPlayer(){
        this.setAudioSource(MediaRecorder.AudioSource.MIC)
                .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                .setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                .setAudioChannels(1);
    }

    private String getFolder(){
        String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.MyRecord/";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdirs();

        return folderPath;

    }

}
