package com.example.dayslar.newmytalk.db.entity;

/**
 * @author Dayslar
 * Класс для описания сущности Token
 */
public class Token {

    private String access_token; // уникальный токен, необходим для доступа к любым ресурсам сервера, выдается сервером
    private String refresh_token; //нужен для обновления access_token, выдается сервером
    private int expires_in; //время жизни токена
    private String token_type; //тип токена
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
