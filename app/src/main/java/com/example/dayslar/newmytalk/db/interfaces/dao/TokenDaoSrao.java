package com.example.dayslar.newmytalk.db.interfaces.dao;

import com.example.dayslar.newmytalk.db.entity.Token;

public interface TokenDaoSrao {

    long insert(Token token);
    int update(Token token);
    int delete();

    Token get();

}
