package com.example.dayslar.newmytalk.receivers;

public class TelephoneConfig {

    public static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String PHONE_STATE = "android.intent.action.PHONE_STATE";

    public static final String EXTRA_STATE = android.telephony.TelephonyManager.EXTRA_STATE;
    public static final String EXTRA_STATE_RUNNING = android.telephony.TelephonyManager.EXTRA_STATE_RINGING;
    public static final String EXTRA_STATE_OFFHOOK = android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK;
    public static final String EXTRA_STATE_IDLE = android.telephony.TelephonyManager.EXTRA_STATE_IDLE;

}
