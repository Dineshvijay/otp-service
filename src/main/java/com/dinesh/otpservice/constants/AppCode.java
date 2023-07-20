package com.dinesh.otpservice.constants;

public enum AppCode {
    INVALID_CLIENT(401, "Invalid client"),
    SUCCESS(200, "SUCCESS"),
    FAILED(400, "SOMETHING WENT WRONG"),
    INVALID_OTP(400, "Invalid OTP"),
    OTP_SENT_SUCCESS(200, "OTP sent successfully"),
    OTP_SENT_FAILED(500, "OTP sent failed"),
    MOBILE_NUMBER_MANDATORY(400, "Mobile number is required"),
    MOBILE_NUMBER_INVALID(400, "INVALID MOBILE NUMBER"),
    CLIENT_ID_INVALID(400, "INVALID ClientId"),
    CLIENT_ID_MANDATORY(400, "ClientId is required"),
    SERVER_EXCEPTION(1000, "Server exception: something went wrong");

    public int code;
    public String message;
    AppCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
