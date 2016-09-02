package com.example.dayslar.newmytalk.db.interfaces.dao;

import com.example.dayslar.newmytalk.db.entity.Record;

import java.util.List;

public interface RecordDAO {

    long insert(Record record);
    void update(Record record);

    void delete(Record record);
    void deleteAll();

    Record get(long id);
    List<Record> getRecords();
}
