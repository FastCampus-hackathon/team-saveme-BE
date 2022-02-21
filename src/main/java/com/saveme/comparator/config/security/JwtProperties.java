package com.saveme.comparator.config.security;

public interface JwtProperties {

    String SECRET = "saram";
    int EXPIRATION_TIME = 864000000; //
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}
