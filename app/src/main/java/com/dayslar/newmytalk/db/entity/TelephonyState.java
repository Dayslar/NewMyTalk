package com.dayslar.newmytalk.db.entity;

public class TelephonyState {

    private String state;

    public String getState() {
        return state;
    }

    public TelephonyState setState(String state) {
        this.state = state;
        return this;
    }

    public final static class State {
        public static final String RINGING = "ringing";
        public static final String RECORDING = "recording";
        public static final String NOT_RINGING = "not_ringing";
    }
}
