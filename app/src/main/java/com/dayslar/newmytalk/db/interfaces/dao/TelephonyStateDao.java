package com.dayslar.newmytalk.db.interfaces.dao;

import com.dayslar.newmytalk.db.entity.TelephonyState;

public interface TelephonyStateDao {

    TelephonyState getTelephonyState();
    void initState();
    void setTelephonyState(TelephonyState state);
}
