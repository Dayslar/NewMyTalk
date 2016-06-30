package com.example.dayslar.newmytalk.database.dao;

import com.example.dayslar.newmytalk.entity.Manager;

import java.util.List;

public interface ManagerDAO {

    void add(Manager manager);
    void add(List<Manager> managers);

    void delete(int id);
    void delete(List<Integer> listManagerId);
}
