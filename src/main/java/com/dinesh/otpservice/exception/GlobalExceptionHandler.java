package com.dinesh.otpservice.exception;

import com.dinesh.otpservice.constants.AppCode;
import com.dinesh.otpservice.util.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleException(Exception e) {
        ResponseException responseException;
        if(e == null || e.getCause() == null || e.getCause().getMessage() == null){
            responseException = new ResponseException(e.getMessage());
        } else {
            responseException = new ResponseException(e.getCause().getMessage());
        }
        return new ResponseWrapper(HttpStatus.INTERNAL_SERVER_ERROR,
                AppCode.SERVER_EXCEPTION.code,
                responseException,
                AppCode.SERVER_EXCEPTION.message).wrap();
    }

    @ExceptionHandler({ClassCastException.class})
    public ResponseEntity<Object>handleClassException(Exception e){
        var responseException = new ResponseException(e.getLocalizedMessage());
        return new ResponseWrapper(HttpStatus.INTERNAL_SERVER_ERROR,
                AppCode.SERVER_EXCEPTION.code,
                responseException,
                AppCode.SERVER_EXCEPTION.message).wrap();
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<Object>handleRequestHeaderException(MissingRequestHeaderException e){
        var responseException = new ResponseException("No clientId is present");
        return new ResponseWrapper(HttpStatus.INTERNAL_SERVER_ERROR,
                AppCode.SERVER_EXCEPTION.code,
                responseException,
                AppCode.SERVER_EXCEPTION.message).wrap();
    }
}
