package com.example.dayslar.newmytalk.database.interfaces.dao;

import com.example.dayslar.newmytalk.database.entity.Record;
import com.example.dayslar.newmytalk.database.interfaces.CurrentRecord;

import java.util.List;

public interface RecordDAO {

    long add(Record record);
    void delete(long id);

    Record get(long id);
    List<Record> getRecords();

    CurrentRecord getCurrentRecord();
}
