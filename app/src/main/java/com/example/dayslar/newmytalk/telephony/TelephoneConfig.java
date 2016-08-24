package com.example.dayslar.newmytalk.telephony;

import  android.telephony.TelephonyManager;

public final class TelephoneConfig {

    public static final String NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

    public static final String EXTRA_STATE = TelephonyManager.EXTRA_STATE;
    public static final String EXTRA_STATE_RUNNING = TelephonyManager.EXTRA_STATE_RINGING;
    public static final String EXTRA_STATE_OFFHOOK = TelephonyManager.EXTRA_STATE_OFFHOOK;
    public static final String EXTRA_STATE_IDLE = TelephonyManager.EXTRA_STATE_IDLE;
    public static final String EXTRA_STATE_MANAGER = "extra_state_manager";

    public static final String TELEPHONE_CODE_SERVICE = "telephone_code_service";

}
