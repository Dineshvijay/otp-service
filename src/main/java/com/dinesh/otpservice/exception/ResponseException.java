package com.dinesh.otpservice.exception;

import lombok.Data;

@Data
public class ResponseException {
    private String message;
    ResponseException(String msg) {
        this.message = msg;
    }
}
