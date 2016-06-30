package com.example.dayslar.newmytalk.database.interfaces;

public interface CurrentRecord {

    void updateInt(String column, int value);
    void updateLong(String column, long value);
    void updateString(String column, String value);
    void updateBoolean(String column, boolean value);
}
