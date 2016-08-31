package com.example.dayslar.newmytalk.network.service.interfaces;

import com.example.dayslar.newmytalk.db.entity.Record;

public interface RecordService {

    void sendRecord(Record record);
    void sendRecordAndFile(Record record);
}
