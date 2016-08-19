package com.example.dayslar.newmytalk.db.interfaces.dao;

import com.example.dayslar.newmytalk.db.entity.Record;

import java.util.List;

public interface RecordDao {

    long insert(Record record);
    void update(Record record, long id);
    void delete(long id);

    Record get(long id);
    List<Record> getRecords();
}
