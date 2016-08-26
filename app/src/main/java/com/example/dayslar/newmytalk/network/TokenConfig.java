package com.example.dayslar.newmytalk.network;

public final class TokenConfig {

    public static final String CLIENT_ID = "my_talk_rest_client";
    public static final String CLIENT_SECRET = "my_talk_rest_secret";

    public final class GrandType {
        public static final String PASSWORD = "password";
        public static final String REFRESH_TOKEN = "refresh_token";
    }

    public final class Scope {
        public static final String TRUST = "trust";
        public static final String READ = "read";
        public static final String WRITE = "write";
        public static final String UPDATE = "update";
    }
}
