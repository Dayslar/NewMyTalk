package com.example.dayslar.newmytalk.database.interfaces;

public interface CurrentRecord {

    void update(String column, int value);
    void update(String column, long value);
    void update(String column, String value);
    void update(String column, boolean value);
}
