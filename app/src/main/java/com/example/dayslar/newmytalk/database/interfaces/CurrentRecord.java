package com.example.dayslar.newmytalk.database.interfaces;

public interface CurrentRecord {

    void update(long recordId, String column, Object value) throws Exception;
}
