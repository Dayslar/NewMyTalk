package com.example.dayslar.newmytalk.database.interfaces.dao;

import com.example.dayslar.newmytalk.database.entity.Manager;

import java.util.List;

public interface ManagerDAO {

    long add(Manager manager);
    List<Long> add(List<Manager> managers);

    void delete(long id);
    void delete(List<Long> listManagerId);

    Manager get(long id);
    List<Manager> getManagers();
}
