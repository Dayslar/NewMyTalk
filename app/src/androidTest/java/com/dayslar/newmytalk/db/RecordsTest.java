package com.dayslar.newmytalk.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dayslar.newmytalk.db.entity.Manager;
import com.dayslar.newmytalk.db.impl.SqlRecordDao;
import com.dayslar.newmytalk.db.entity.Record;
import com.dayslar.newmytalk.db.interfaces.dao.RecordDao;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecordsTest {

    @Test
    public void addRecord() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao recordDao = SqlRecordDao.getInstance(appContext);

        Record record = new Record()
                .setManager(new Manager().setId(2).setName("Коноплич Ольга"))
                .setCallPhone("+375295642388")
                .setMyPhone("+375295642388")
                .setCallTime(146734857343L)
                .setStartRecord(146734857344L)
                .setEndRecord(146734857399L)
                .setContactName("Питюня")
                .setAnswer(true)
                .setIncoming(true)
                .setFileName("1.mp3");

        recordDao.insert(record);

    }

    @Test
    public void deleteRecord() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao recordDao = SqlRecordDao.getInstance(appContext);

        recordDao.delete(new Record().setId(10));
    }

    @Test
    public void getRecord() throws Exception  {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao recordDao = SqlRecordDao.getInstance(appContext);

        recordDao.get(0);
    }

    @Test
    public void getRecords() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RecordDao recordDao = SqlRecordDao.getInstance(appContext);

        recordDao.getRecords();
    }
}
