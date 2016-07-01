package com.example.dayslar.newmytalk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.dayslar.newmytalk.database.impl.RecordDaoImpl;
import com.example.dayslar.newmytalk.entity.Record;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecordsTest {

    @Test
    public void addRecord() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        Record record = new Record();
        record.setSubdivision_id(1);
        record.setManager_id(2);
        record.setCallNumber("+375295642388");
        record.setPhoneNumber("+375295642388");
        record.setCallTime(146734857343L);
        record.setStartRecord(146734857344L);
        record.setEndRecord(146734857399L);
        record.setContactName("Питюня");
        record.setAnswer(true);
        record.setIncoming(true);
        record.setPatch("d://");
        record.setFileName("1.mp3");

        long id = new RecordDaoImpl(appContext).add(record);

    }

    @Test
    public void deleteRecord() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new RecordDaoImpl(appContext).delete(2);
    }

    @Test
    public void getRecord() throws Exception  {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new RecordDaoImpl(appContext).get(3);
    }

    @Test
    public void getRecords() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        new RecordDaoImpl(appContext).getRecords();
    }
}
