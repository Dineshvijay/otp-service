package com.dinesh.otpservice.service;

import com.dinesh.otpservice.dto.OTPGenerateDto;
import com.dinesh.otpservice.dto.OTPVerifyDto;
import org.springframework.http.ResponseEntity;

public interface OtpService {

     ResponseEntity<Object> find(OTPVerifyDto userDto, String clientId);
     ResponseEntity<Object> generateOTP(OTPGenerateDto user, String clientId);

}
