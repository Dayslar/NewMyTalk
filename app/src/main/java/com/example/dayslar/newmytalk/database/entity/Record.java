package com.example.dayslar.newmytalk.database.entity;

public class Record {

    private int _id;
    private int subdivision_id;
    private int manager_id;
    private String patch;
    private String fileName;
    private String phoneNumber;
    private String callNumber;
    private long startRecord;
    private long endRecord;
    private long callTime;
    private String contactName;
    private boolean answer;
    private boolean incoming;

    public Record(){

    }

    public int get_id() {
        return _id;
    }

    public Record set_id(int _id) {
        this._id = _id;
        return this;
    }

    public int getSubdivision_id() {
        return subdivision_id;
    }

    public Record setSubdivision_id(int subdivision_id) {
        this.subdivision_id = subdivision_id;
        return this;
    }

    public int getManager_id() {
        return manager_id;
    }

    public Record setManager_id(int manager_id) {
        this.manager_id = manager_id;
        return this;
    }

    public String getPatch() {
        return patch;
    }

    public Record setPatch(String patch) {
        this.patch = patch;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Record setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Record setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public Record setCallNumber(String callNumber) {
        this.callNumber = callNumber;
        return this;
    }

    public long getStartRecord() {
        return startRecord;
    }

    public Record setStartRecord(long startRecord) {
        this.startRecord = startRecord;
        return this;
    }

    public long getEndRecord() {
        return endRecord;
    }

    public Record setEndRecord(long endRecord) {
        this.endRecord = endRecord;
        return this;
    }

    public long getCallTime() {
        return callTime;
    }

    public Record setCallTime(long callTime) {
        this.callTime = callTime;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public Record setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public boolean isAnswer() {
        return answer;
    }

    public Record setAnswer(boolean answer) {
        this.answer = answer;
        return this;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public Record setIncoming(boolean incoming) {
        this.incoming = incoming;
        return this;
    }

    @Override
    public String toString() {
        return "Record{" +
                "_id=" + _id +
                ", subdivision_id=" + subdivision_id +
                ", manager_id=" + manager_id +
                ", patch='" + patch + '\'' +
                ", fileName='" + fileName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", startRecord=" + startRecord +
                ", endRecord=" + endRecord +
                ", callTime=" + callTime +
                ", contactName='" + contactName + '\'' +
                ", answer=" + answer +
                ", incoming=" + incoming +
                '}';
    }
}
