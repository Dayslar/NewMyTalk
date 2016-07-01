package com.example.dayslar.newmytalk.database.dao;

import com.example.dayslar.newmytalk.entity.Manager;

import java.util.List;

public interface ManagerDAO {

    long add(Manager manager);
    List<Long> add(List<Manager> managers);

    void delete(int id);
    void delete(List<Integer> listManagerId);

    Manager get(int id);
    List<Manager> getManagers();
}
