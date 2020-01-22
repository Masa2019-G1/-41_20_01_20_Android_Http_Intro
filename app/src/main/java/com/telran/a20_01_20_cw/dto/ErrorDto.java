package com.telran.a20_01_20_cw.dto;

public class ErrorDto {
    int code;
    String message;

    public ErrorDto() {
    }

    public ErrorDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
