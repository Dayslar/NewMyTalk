package com.dayslar.newmytalk.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dayslar.newmytalk.db.entity.Token;
import com.dayslar.newmytalk.db.impl.SqlTokenDao;
import com.dayslar.newmytalk.db.interfaces.dao.TokenDao;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TokenTest {

    @Test
    public void addToken() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenDao TokenDao = SqlTokenDao.getInstance(appContext);

        Token token = new Token()
                .setAccess_token("fhgfjsdhgfjsdhgfjsd")
                .setRefresh_token("fbhsdjgfjdhsfgjdsfhsd")
                .setExpires_in(455)
                .setScope("write")
                .setToken_type("bearer");

        TokenDao.insert(token);

    }

    @Test
    public void updateToken() throws Exception  {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenDao TokenDao = SqlTokenDao.getInstance(appContext);

        TokenDao.update(new Token().setAccess_token("gdjhfgsdjhfgsdjf"));
    }

    @Test
    public void getToken() throws Exception  {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenDao TokenDao = SqlTokenDao.getInstance(appContext);

        TokenDao.get();
    }


    @Test
    public void deleteToken() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenDao TokenDao = SqlTokenDao.getInstance(appContext);

        TokenDao.delete();
    }

}
