package com.dinesh.otpservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OTPVerifyDto {
    private String id;
    private Integer otp;
}