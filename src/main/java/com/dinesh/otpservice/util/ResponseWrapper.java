package com.dinesh.otpservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper {
    private final HttpStatus httpStatus;
    private final Object data;
    private final int code;
    private String message;

    public ResponseWrapper(HttpStatus httpStatus, int code, Object data, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseEntity<Object> wrap() {
        var map = new HashMap<String, Object>();
        map.put("status", responseStatus());
        map.put("data", this.data);
        return new ResponseEntity<>(map, httpStatus);
    }

    private Map<String, Object> responseStatus() {
        var map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("message", this.message);
        map.put("timestamp", ZonedDateTime.now(ZoneId.of("Z")));
        return map;
    }

}
