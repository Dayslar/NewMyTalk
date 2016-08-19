package com.example.dayslar.newmytalk.db.entity;

public class Record {

    private long _id;
    private long callTime;
    private String callPhone;
    private String myPhone;
    private boolean answer;
    private boolean incoming;
    private long startRecord;
    private long endRecord;
    private int managerId;
    private String contactName;
    private String fileName;

    public long getId() {
        return _id;
    }

    public Record setId(long _id) {
        this._id = _id;
        return this;
    }

    public long getCallTime() {
        return callTime;
    }

    public Record setCallTime(long callTime) {
        this.callTime = callTime;
        return this;
    }

    public String getCallPhone() {
        return callPhone;
    }

    public Record setCallPhone(String callPhone) {
        this.callPhone = callPhone;
        return this;
    }

    public String getMyPhone() {
        return myPhone;
    }

    public Record setMyPhone(String myPhone) {
        this.myPhone = myPhone;
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

    public int getManagerId() {
        return managerId;
    }

    public Record setManagerId(int managerId) {
        this.managerId = managerId;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public Record setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Record setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String toString() {
        return "Record{" +
                "_id=" + _id +
                ", callTime=" + callTime +
                ", callPhone='" + callPhone + '\'' +
                ", myPhone='" + myPhone + '\'' +
                ", answer=" + answer +
                ", incoming=" + incoming +
                ", startRecord=" + startRecord +
                ", endRecord=" + endRecord +
                ", managerId=" + managerId +
                ", contactName='" + contactName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public static Record emptyRecord() {
        return new Record();
    }
}
