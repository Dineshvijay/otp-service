package com.dinesh.otpservice.client;

import com.dinesh.otpservice.dto.SMSDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
@Slf4j
public class RestClientImpl implements RestClient {
    private final RestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();
    public RestClientImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri("https://www.fast2sms.com/dev")
                .setConnectTimeout(Duration.ofSeconds(20))
                .setReadTimeout(Duration.ofSeconds(20))
                .build();
        headers.set("authorization", System.getenv("FAST_SMS_AUTH"));
    }

    @Override
    public ResponseEntity<SMSDto> sendOTPSMS(String countryCode, String mobileNumber, Integer otpCode) {
        log.info("sendOTPSMS called");
        headers.set("content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("route", "otp");
        map.add("variables_values", String.valueOf(otpCode));
        map.add("numbers", mobileNumber);
        HttpEntity<MultiValueMap> entity = new HttpEntity<>(map, headers);
        ResponseEntity<SMSDto> fastSMSDto = restTemplate.exchange(
                "/bulkV2",
                HttpMethod.POST,
                entity,
                SMSDto.class);
        log.info("sendOTPSMS end {} " , fastSMSDto);
        return fastSMSDto;
    }

    @Override
    public ResponseEntity<SMSDto> sendQuickSMS(String countryCode, String mobileNumber, Integer otpCode) {
        /*var message = "<#> Your Example app code: " + otpCode + "\nErt436gwd";
        headers.set("authorization", System.getenv("FAST_SMS_AUTH"));
        headers.set("content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("route", "q");
        map.add("message", message);
        map.add("numbers", mobileNumber);
        map.add("language", "english");
        HttpEntity<MultiValueMap> entity = new HttpEntity<>(map, headers);
        ResponseEntity<SMSDto> fastSMSDto = restTemplate.exchange(
                "https://www.fast2sms.com/dev/bulkV2",
                HttpMethod.POST,
                entity,
                SMSDto.class);
        return fastSMSDto;
         */
        SMSDto smsDto = new SMSDto();
        smsDto.setRequest_id("1");
        smsDto.setSuccess(true);
        return new ResponseEntity<>(smsDto, HttpStatus.OK);
    }

}

