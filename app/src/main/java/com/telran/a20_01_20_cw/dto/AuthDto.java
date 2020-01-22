package com.telran.a20_01_20_cw.dto;

public class AuthDto {
    String token;

    public AuthDto() {
    }

    public AuthDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
