package com.example.dayslar.newmytalk.db.interfaces.dao;

import com.example.dayslar.newmytalk.db.entity.Manager;

import java.util.List;

public interface IManagerDao {

    long insert(Manager manager);
    List<Long> insert(List<Manager> managers);

    void delete(long id);
    void deleteAll();

    Manager get(long id);
    List<Manager> getManagers();
}
