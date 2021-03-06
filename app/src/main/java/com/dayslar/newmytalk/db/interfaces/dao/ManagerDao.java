package com.dayslar.newmytalk.db.interfaces.dao;

import com.dayslar.newmytalk.db.entity.Manager;

import java.util.List;

public interface ManagerDao {

    long insert(Manager manager);
    List<Long> insert(List<Manager> managers);

    void delete(long id);
    void deleteAll();

    Manager get(long id);
    List<Manager> getManagers();
}
