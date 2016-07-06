package com.example.dayslar.newmytalk.telephony.interfaces;

import android.content.Intent;

public interface TelephonyHandler {

    void outgoingCall(Intent intent);
    void runningCall(Intent intent);
    void offhookCall(Intent intent);
    void idleCall(Intent intent);

}
