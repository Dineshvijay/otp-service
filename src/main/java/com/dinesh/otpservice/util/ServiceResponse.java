package com.dinesh.otpservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse {
    private final HttpStatus httpStatus;
    private final Object data;
    private final int code;
    private final String message;

    public ServiceResponse(HttpStatus status, int code,  Object data,String message) {
        this.httpStatus = status;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ServiceResponse status(HttpStatus status) {
        return new ServiceResponse(status,  code, this.data, this.message);
    }

    public ServiceResponse code(int code) {
       return new ServiceResponse(this.httpStatus,  code, this.data, this.message);
    }

    public ServiceResponse body(Object body) {
        return new ServiceResponse(this.httpStatus, this.code, body, this.message);
    }

    public ServiceResponse message(String message) {
        return new ServiceResponse(this.httpStatus, this.code,  this.data, message);
    }

    public ServiceResponse build() {
        return new ServiceResponse(this.httpStatus,  this.code, getBodyData(), this.message);
    }

    private HashMap getBodyData() {
        var map = new HashMap<String, Object>();
        map.put("status", responseStatus());
        map.put("data", this.data);
        return map;
    }

    private Map<String, Object> responseStatus() {
        var map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("message", this.message);
        return map;
    }
}
