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

        Record record = new Record()
                .setSubdivision_id(1)
                .setManager_id(2)
                .setCallNumber("+375295642388")
                .setPhoneNumber("+375295642388")
                .setCallTime(146734857343L)
                .setStartRecord(146734857344L)
                .setEndRecord(146734857399L)
                .setContactName("Питюня")
                .setAnswer(true)
                .setIncoming(true)
                .setPatch("d://")
                .setFileName("1.mp3");

        new RecordDaoImpl(appContext).add(record);

    }

    @Test
    public void deleteRecord() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        new RecordDaoImpl(appContext).delete(10);
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
