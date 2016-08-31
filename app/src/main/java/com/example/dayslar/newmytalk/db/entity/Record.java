package com.example.dayslar.newmytalk.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dayslar
 * Клаас описывающий запись
 */
public class Record {

    private long _id; //уникальный id записи
    private long callTime; //время звонка
    private String callPhone; //телефон с(на) которого(ый) звонили
    private String myPhone; //свой телефон
    private boolean answer; //булево значение ответ/без ответа
    private boolean incoming; //булево значение входящий/исходящий
    private long startRecord; //время начала записи
    private long endRecord; //время окончания записи
    private Manager manager; //менеджер ответивщий на звонок, может быть null
    private String contactName; // имя контакта
    private String fileName; //имя файла записи

    @JsonIgnore
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

    public Manager getManager() {
        return manager;
    }

    public Record setManager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public Record setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    @JsonIgnore
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
                ", manager=" + manager +
                ", contactName='" + contactName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public static Record emptyRecord() {
        return new Record();
    }
}
