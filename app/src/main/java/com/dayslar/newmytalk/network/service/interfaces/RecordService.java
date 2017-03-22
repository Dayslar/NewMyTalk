package com.dayslar.newmytalk.network.service.interfaces;

import com.dayslar.newmytalk.db.entity.Record;

public interface RecordService {

    void sendRecord(Record record);
    void sendRecordAndFile(Record record);
}
