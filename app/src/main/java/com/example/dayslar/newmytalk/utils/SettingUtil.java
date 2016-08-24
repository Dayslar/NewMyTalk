package com.example.dayslar.newmytalk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaRecorder;

import com.example.dayslar.newmytalk.R;

public class SettingUtil {

    private static SettingUtil instance;

    private Resources resources;
    private SharedPreferences preferences;
    private Setting setting;


    private SettingUtil(Context context){
        setting = new Setting();
        resources = context.getResources();
        preferences = context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());

        loadSetting();
    }

    public static SettingUtil getInstance(Context context){
        if (instance == null) {
            synchronized (SettingUtil.class) {
                if (instance == null)
                    instance = new SettingUtil(context);
            }
        }

        return instance;
    }

    public Setting getSetting() {
        return setting;
    }

    public void loadSetting(){
        setting.setRecordingActive(preferences.getBoolean(resources.getString(R.string.chActiveRecordKey), false))
                .setManagerActive(preferences.getBoolean(resources.getString(R.string.chActiveManagerKey), false))
                .setAdminActive(preferences.getBoolean(resources.getString(R.string.chAdminKey), false))
                .setUnloadActive(preferences.getBoolean(resources.getString(R.string.chUnLoadRecordKey), false))

                .setDelay(Integer.parseInt(preferences.getString(resources.getString(R.string.etDelayRecordIsCallKey), "10")))

                .setServerIp(preferences.getString(resources.getString(R.string.etSettingIpKey), "192.168.21.112"))
                .setServerPort(preferences.getString(resources.getString(R.string.etSettingPortKey), "8080"));
    }

    public void loadSettingForPlayer(){
        String outputFormat = preferences.getString(resources.getString(R.string.listOutputFormatKey), resources.getString(R.string.listValueDefault));
        String audioSource = preferences.getString(resources.getString(R.string.listAudioSourceKey), resources.getString(R.string.listValueDefault));
        String audioEncoder = preferences.getString(resources.getString(R.string.listAudioEncoderKey), resources.getString(R.string.listAudioEncoderKey));

        int audioChanels = preferences.getInt(resources.getString(R.string.chanelRecordingKey), 1);

        //output format
        if (outputFormat.equals(resources.getString(R.string.listOutputFormatAac)))
            setting.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);

        else if (outputFormat.equals(resources.getString(R.string.listOutputFormatMPEG4)))
            setting.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        else if (outputFormat.equals(resources.getString(R.string.listOutputFormat3gpp)))
            setting.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        else setting.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        //audio source
        if (audioSource.equals(resources.getString(R.string.listAudioSourceVoiceCall)))
            setting.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

        else if (audioSource.equals(resources.getString(R.string.listAudioSourceMic)))
            setting.setAudioSource(MediaRecorder.AudioSource.MIC);

        else if (audioSource.equals(resources.getString(R.string.listAudioSourceVoiceDowlink)))
            setting.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);

        else if (audioSource.equals(resources.getString(R.string.listAudioSourceVoiceUplink)))
            setting.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK);

        else if (audioSource.equals(resources.getString(R.string.listAudioSourceVoiceCamcorder)))
            setting.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);

        else setting.setAudioSource(MediaRecorder.AudioSource.MIC);


        //audio encoder
        if (audioEncoder.equals(resources.getString(R.string.listAudioEncoderAac)))
            setting.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        else if (audioEncoder.equals(resources.getString(R.string.listAudioEncoderAacEld)))
            setting.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);

        else if (audioEncoder.equals(resources.getString(R.string.listAudioEncoderAmrNb)))
            setting.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        else if (audioEncoder.equals(resources.getString(R.string.listAudioEncoderAmrWb)))
            setting.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

        else if (audioEncoder.equals(resources.getString(R.string.listAudioEncoderHeAac)))
            setting.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);

        else setting.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        setting.setAudioChannels(audioChanels);
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }
}
