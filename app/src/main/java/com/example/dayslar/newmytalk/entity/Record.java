package com.example.dayslar.newmytalk.entity;

public class Record {

    private int _id;
    private int subdivision_id;
    private int manager_id;
    private String patch;
    private String fileName;
    private String phoneNumber;
    private String callNumber;
    private int duration;
    private long callTime;
    private String contactName;
    private boolean answer;
    private boolean incoming;

    public Record(int id) {
        _id = id;
    }

    public int get_id() {
        return _id;
    }

    public int getSubdivision_id() {
        return subdivision_id;
    }

    public void setSubdivision_id(int subdivision_id) {
        this.subdivision_id = subdivision_id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }
}
