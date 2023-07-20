package com.dinesh.otpservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OTPResponseDto {
    private String id;
    private Integer otp;
    private String mobileNumber;
    private String countryCode;
}
