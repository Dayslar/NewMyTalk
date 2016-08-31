package com.example.dayslar.newmytalk.db.config;

/**
 * @author Dayslar
 * Класс описывает данные по БД, такие как Имя Базы данных
 * Имя таблиц в базе
 * Версия базы данных
 */
public final class DbConfig {

    public static final String DB_NAME = "RecordDB"; //Database name
    public static final String RECORD_TABLE_NAME = "RecordTable"; //Record Table Name
    public static final String MANAGER_TABLE_NAME = "ManagerTable"; //Manager Table Name
    public static final String TOKEN_TABLE_NAME = "TokenTable"; //Token Table Name


    public static final String COLUMN_ID = "_id"; //Имя поля id для любой таблицы

    public static final int DB_VERSION = 5; //database version

}
