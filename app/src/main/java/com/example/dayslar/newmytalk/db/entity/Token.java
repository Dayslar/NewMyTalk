package com.example.dayslar.newmytalk.db.entity;

public class Token {

    private String access_token;
    private String refresh_token;
    private int expires_in;
    private String token_type;
    private String scope;

    public int getExpires_in() {
        return expires_in;
    }

    public Token setExpires_in(int expires_in) {
        this.expires_in = expires_in;
        return this;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Token setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public Token setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
        return this;
    }

    public String getToken_type() {
        return token_type;
    }

    public Token setToken_type(String token_type) {
        this.token_type = token_type;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Token setScope(String scope) {
        this.scope = scope;
        return this;
    }
}
