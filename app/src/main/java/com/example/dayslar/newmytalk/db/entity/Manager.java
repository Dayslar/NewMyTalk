package com.example.dayslar.newmytalk.db.entity;

public class Manager {

    private int _id;
    private String name;
    private String photoPatch;


    public int getId() {
        return _id;
    }

    public Manager setId(int _id) {
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
                ", name='" + name + '\'' +
                ", photoPatch='" + photoPatch + '\'' +
                '}';
    }
}
