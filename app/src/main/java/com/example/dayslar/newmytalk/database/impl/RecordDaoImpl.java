package com.example.dayslar.newmytalk.database.impl;

import android.content.Context;

import com.example.dayslar.newmytalk.database.DataBaseController;
import com.example.dayslar.newmytalk.database.dao.RecordDAO;
import com.example.dayslar.newmytalk.entity.Record;

import java.util.List;

public class RecordDaoImpl implements RecordDAO {

    private DataBaseController dbController;

    public RecordDaoImpl(Context context){
        dbController = new DataBaseController(context);
    }

    @Override
    public void add(Record record) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Record get(int id) {
        return null;
    }

    @Override
    public List<Record> getRecords() {
        return null;
    }
}
