package com.example.dayslar.newmytalk.recorder;

import android.media.MediaRecorder;
import com.example.dayslar.newmytalk.utils.MyLogger;
import java.io.IOException;

public class MediaRecorderImpl implements Recorder {

    private static MediaRecorderImpl instance;

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    private int outputFormat;
    private int audioSource;
    private int audioEncoder;
    private int audioChannels;

    public static MediaRecorderImpl getInstance(){
        if (instance == null){
            instance = new MediaRecorderImpl();
        }

        return instance;
    }

    private MediaRecorderImpl(){

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
    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    @Override
    public void setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    @Override
    public void setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
    }

    @Override
    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    private void initRecorder(String outputFile) throws IOException {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(audioSource);
        mediaRecorder.setOutputFormat(outputFormat);
        mediaRecorder.setAudioEncoder(audioEncoder);
        mediaRecorder.setAudioChannels(audioChannels);
        mediaRecorder.setOutputFile(outputFile);

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

}
