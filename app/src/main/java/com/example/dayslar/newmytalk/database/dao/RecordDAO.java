package com.example.dayslar.newmytalk.database.dao;

import com.example.dayslar.newmytalk.entity.Record;

import java.util.List;

public interface RecordDAO {

    void add(Record record);
    void delete(int id);

    Record get(int id);
    List<Record> getRecords();
}
