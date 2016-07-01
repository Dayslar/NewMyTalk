package com.example.dayslar.newmytalk.recorder;


import android.media.MediaRecorder;

import com.example.dayslar.newmytalk.utils.MyLogger;

import java.io.IOException;

public class RecorderImpl implements Recorder {

    private static RecorderImpl instance;

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    public static RecorderImpl getInstance(){
        if (instance == null){
            instance = new RecorderImpl();
        }

        return instance;
    }

    private RecorderImpl(){
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public void startRecord(String outputFile) {
        if (!isRecording && mediaRecorder != null){
            try {
                mediaRecorder.release();
                mediaRecorder.setOutputFile(outputFile);
                mediaRecorder.prepare();
                mediaRecorder.start();

                isRecording = true;
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
            mediaRecorder.stop();
            isRecording = false;

            MyLogger.print(this.getClass(), MyLogger.LOG_DEBUG, "Запись остановлена");
        }
    }

    @Override
    public void setAudioSource(int audioSource) {
        mediaRecorder.setAudioSource(audioSource);
    }

    @Override
    public void setOutputFormat(int outputFormat) {
        mediaRecorder.setOutputFormat(outputFormat);
    }

    @Override
    public void setAudioEncoder(int audioEncoder) {
        mediaRecorder.setAudioEncoder(audioEncoder);
    }

    @Override
    public void setAudioChannels(int audioChannels) {
        mediaRecorder.setAudioChannels(1);
    }
}
