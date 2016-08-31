package com.example.dayslar.newmytalk.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dayslar
 * Класс для описания сущность Manager
 */
public class Manager {

    private int _id; //уникальный id менеджера
    private String name; //имя менеджера
    private String photoPatch; //путь к файлу с картинкой на устройстве


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

    @JsonIgnore
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
