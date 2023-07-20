package com.dinesh.otpservice.client;

import com.dinesh.otpservice.dto.SMSDto;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TwilioSMSClient {

    TwilioSMSClient() {
        var account = System.getenv("TWILLIO_ACCOUNT");
        var auth = System.getenv("TWILLIO_AUTH");
        Twilio.init(account, auth);
    }
    public ResponseEntity<SMSDto> sendOTP(String countryCode, String mobileNumber){
        var serviceSId = System.getenv("TWILLIO_SERVICE_SID");
        var phoneNumber = countryCode + mobileNumber;
        Verification verification = Verification.creator(
                serviceSId,
                phoneNumber,
                        "sms").create();
        var smsDto = new SMSDto();
        smsDto.setSuccess(verification.getValid());
        smsDto.setRequest_id(verification.getServiceSid());
        var httpStatus = verification.getValid() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(smsDto, httpStatus);
    }

    public ResponseEntity<SMSDto> verifyOTP(String countryCode, String mobileNumber, String code){
        var phoneNumber = countryCode + mobileNumber;
        var serviceSId = System.getenv("TWILLIO_SERVICE_SID");
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        serviceSId)
                    .setTo(phoneNumber)
                    .setCode(code)
                    .create();
        var smsDto = new SMSDto();
        smsDto.setSuccess(verificationCheck.getValid());
        smsDto.setRequest_id(verificationCheck.getServiceSid());
        var httpStatus = verificationCheck.getValid() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(smsDto, httpStatus);
    }
}
