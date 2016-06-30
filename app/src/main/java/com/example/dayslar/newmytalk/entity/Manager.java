package com.example.dayslar.newmytalk.entity;

public class Manager {

    private int _id;
    private int manager_id;
    private String name;
    private String photoPatch;

    public int get_id() {
        return _id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPatch() {
        return photoPatch;
    }

    public void setPhotoPatch(String photoPatch) {
        this.photoPatch = photoPatch;
    }
}
