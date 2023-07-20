package com.dinesh.otpservice.client;

import com.dinesh.otpservice.dto.SMSDto;
import org.springframework.http.ResponseEntity;

public interface RestClient {

    ResponseEntity<SMSDto> sendOTPSMS(String countryCode, String mobileNumber, Integer otpCode);
    ResponseEntity<SMSDto> sendQuickSMS(String countryCode, String mobileNumber, Integer otpCode);
}