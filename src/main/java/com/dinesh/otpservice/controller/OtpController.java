package com.dinesh.otpservice.controller;

import com.dinesh.otpservice.dto.OTPGenerateDto;
import com.dinesh.otpservice.dto.OTPVerifyDto;
import com.dinesh.otpservice.service.OtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@Api(tags = {"OTP services"})
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService){
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    @ApiOperation(value = "GenerateOTP")
    ResponseEntity<Object> sendOTP(@RequestHeader(value="clientId") String clientId,
                                   @RequestBody OTPGenerateDto user) {
        return otpService.generateOTP(user, clientId);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "VerifyOTP")
    ResponseEntity<Object> verifyOTP(@RequestHeader(value = "clientId") String clientId,
                                     @RequestBody OTPVerifyDto userDto) {
        return otpService.find(userDto, clientId);
    }

}
