package com.dayslar.newmytalk.db.interfaces.dao;

import com.dayslar.newmytalk.db.entity.Record;

import java.util.List;

public interface RecordDao {

    long insert(Record record);
    void update(Record record);

    void delete(Record record);
    void deleteAll();

    Record get(long id);
    Record last();
    List<Record> getRecords();
}
