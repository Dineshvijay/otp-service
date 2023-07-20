package com.dinesh.otpservice.service;

import com.dinesh.otpservice.client.RestClient;
import com.dinesh.otpservice.constants.AppCode;
import com.dinesh.otpservice.dto.OTPGenerateDto;
import com.dinesh.otpservice.dto.OTPResponseDto;
import com.dinesh.otpservice.dto.OTPVerifyDto;
import com.dinesh.otpservice.modal.User;
import com.dinesh.otpservice.repository.OtpDao;
import com.dinesh.otpservice.util.OTPNumGenerator;
import com.dinesh.otpservice.util.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Slf4j
public class OtpServiceImp implements OtpService{
    private final OtpDao otpDao;
    private final RestClient restClient;

    public OtpServiceImp(OtpDao otpDao, RestClient restClient) {
        this.otpDao = otpDao;
        this.restClient = restClient;
    }

    @Override
    public ResponseEntity<Object> generateOTP(OTPGenerateDto user, String clientId) {
        validateClientId(clientId);
        var userObj = getUser(user, clientId);
        var res = restClient.sendQuickSMS(userObj.getCountryCode(), userObj.getMobileNumber(), userObj.getOtp());
        if(res != null && res.getBody().isSuccess()) {
            var userId = otpDao.save(userObj);
            if(userId != null && !userId.isEmpty()){
                OTPResponseDto userDto = new OTPResponseDto();
                userDto.setId(userId);
                log.info(userId);
                return new ResponseWrapper(HttpStatus.OK,
                        AppCode.OTP_SENT_SUCCESS.code,
                        userDto,
                        AppCode.OTP_SENT_SUCCESS.message).wrap();
            }
        }
        return new ResponseWrapper(HttpStatus.OK,
                AppCode.FAILED.code,
                null,
                AppCode.FAILED.message).wrap();
    }

    @Override
    public ResponseEntity<Object> find(OTPVerifyDto userDto, String clientId) {
        var key = clientId + ":" + userDto.getId().replace("-", "");
        var result = otpDao.find(key, userDto.getId());
        if(result != null) {
            var redisOTP = result.getOtp();
            var userOTP = userDto.getOtp();
            if(redisOTP == userOTP) {
                return new ResponseWrapper(HttpStatus.OK, AppCode.SUCCESS.code,
                        result,
                        AppCode.SUCCESS.message).wrap();
            } else {
                return new ResponseWrapper(HttpStatus.BAD_REQUEST,
                        AppCode.INVALID_OTP.code, null,
                        AppCode.INVALID_OTP.message).wrap();
            }
        }
        return new ResponseWrapper(HttpStatus.BAD_REQUEST,
                AppCode.FAILED.code, null,
                AppCode.FAILED.message).wrap();
    }

    private User getUser(OTPGenerateDto userDto, String clientId) {
        if(userDto.getMobileNumber() == null && userDto.getCountryCode() == null) {
            throw new NullPointerException(AppCode.MOBILE_NUMBER_MANDATORY.message);
        } else if (userDto.getMobileNumber().length() != 10) {
            throw new IllegalArgumentException(AppCode.MOBILE_NUMBER_INVALID.message);
        }
        int otp = OTPNumGenerator.generate();
        User user = new User();
        user.setMobileNumber(userDto.getMobileNumber());
        user.setClientId(clientId);
        user.setId(UUID.randomUUID().toString());
        user.setCountryCode(userDto.getCountryCode());
        user.setExpirationInSeconds(120L);
        user.setOtp(otp);
        return user;
    }

    public void validateClientId(String clientId) {
        if (clientId == null || !(clientId.trim().length() > 0)) {
             throw new IllegalArgumentException(AppCode.CLIENT_ID_MANDATORY.message);
        } else if (!clientId.equals("demo-app")) {
            throw new IllegalArgumentException(AppCode.CLIENT_ID_INVALID.message);
        }
    }
}
