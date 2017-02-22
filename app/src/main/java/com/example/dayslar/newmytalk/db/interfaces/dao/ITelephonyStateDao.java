package com.example.dayslar.newmytalk.db.interfaces.dao;

import com.example.dayslar.newmytalk.db.entity.TelephonyState;

public interface ITelephonyStateDao {

    TelephonyState getTelephonyState();
    void initState();
    void setTelephonyState(TelephonyState state);
}
