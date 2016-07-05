package com.example.dayslar.newmytalk.database.interfaces.dao;

import com.example.dayslar.newmytalk.database.entity.Manager;

import java.util.List;

public interface ManagerDAO {

    long add(Manager manager);
    List<Long> add(List<Manager> managers);

    void delete(int id);
    void delete(List<Integer> listManagerId);

    Manager get(int id);
    List<Manager> getManagers();
}
