package com.example.dayslar.newmytalk.database.entity;

public class Manager {

    private int _id;
    private int manager_id;
    private String name;
    private String photoPatch;

    public Manager() {

    }

    public int getManager_id() {
        return manager_id;
    }

    public Manager setManager_id(int manager_id) {
        this.manager_id = manager_id;
        return this;
    }

    public int get_id() {
        return _id;
    }

    public Manager set_id(int _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Manager setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhotoPatch() {
        return photoPatch;
    }

    public Manager setPhotoPatch(String photoPatch) {
        this.photoPatch = photoPatch;
        return this;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "_id=" + _id +
                ", manager_id=" + manager_id +
                ", name='" + name + '\'' +
                ", photoPatch='" + photoPatch + '\'' +
                '}';
    }
}
