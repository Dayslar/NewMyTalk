package com.example.dayslar.newmytalk.database.interfaces;

public interface CurrentRecord {

    /**
     * Method for update information in given column
     * @param recordId -  unique index record in table
     * @param column - column in table for update
     * @param value - information for update
     */


    void update(long recordId, String column, Object value);
}
