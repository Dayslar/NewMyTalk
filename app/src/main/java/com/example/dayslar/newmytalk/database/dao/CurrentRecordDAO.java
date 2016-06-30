package com.example.dayslar.newmytalk.database.dao;

import com.example.dayslar.newmytalk.entity.Record;

public interface CurrentRecordDAO {

    Record get();
    void clear();
}
