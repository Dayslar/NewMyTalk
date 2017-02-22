package com.example.dayslar.newmytalk.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.db.entity.Manager;
import com.example.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.example.dayslar.newmytalk.db.entity.Record;
import com.example.dayslar.newmytalk.db.interfaces.dao.RecordDao;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecordsTest {

    @Test
    public void addRecord() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao RecordDao = SqlRecordDao.getInstance(appContext);

        Record record = new Record()
                .setManager(new Manager().setId(2).setName("Коноплич ольга"))
                .setCallPhone("+375295642388")
                .setMyPhone("+375295642388")
                .setCallTime(146734857343L)
                .setStartRecord(146734857344L)
                .setEndRecord(146734857399L)
                .setContactName("Питюня")
                .setAnswer(true)
                .setIncoming(true)
                .setFileName("1.mp3");

        RecordDao.insert(record);

    }

    @Test
    public void deleteRecord() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao RecordDao = SqlRecordDao.getInstance(appContext);

        RecordDao.delete(new Record().setId(10));
    }

    @Test
    public void getRecord() throws Exception  {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao RecordDao = SqlRecordDao.getInstance(appContext);

        RecordDao.get(3);
    }

    @Test
    public void getRecords() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao RecordDao = SqlRecordDao.getInstance(appContext);

        RecordDao.getRecords();
    }
}