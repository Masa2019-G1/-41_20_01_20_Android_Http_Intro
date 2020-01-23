package com.telran.a20_01_20_cw.dto;

public class DeleteStatusDto {
    String status;

    public DeleteStatusDto() {
    }

    public DeleteStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeleteStatusDto{" +
                "status='" + status + '\'' +
                '}';
    }
}
