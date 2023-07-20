package com.dinesh.otpservice.service;

import com.dinesh.otpservice.client.RestClient;
import com.dinesh.otpservice.dto.SMSDto;
import com.dinesh.otpservice.dto.OTPGenerateDto;
import com.dinesh.otpservice.dto.OTPResponseDto;
import com.dinesh.otpservice.modal.User;
import com.dinesh.otpservice.repository.OtpDao;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtpServiceImpTest {
    @Autowired
    private OtpService service;
    @Autowired
    private RestClient restClient;
    @MockBean
    private OtpDao repository;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void fetchAllUsersTest() {
        List<User> mockUsers = List.of(new User("id1", 10L, "9999999990", "demo-app",1000 , "+91"),
                new User("id1", 10L, "9999999992", "demo-app",2000 , "+91"));
        when(repository.fetchAllUser()).thenReturn(mockUsers);
        Map<String, Object> object = (Map<String, Object>) service.findAllUser().getBody();
        List<User> userList = (List<User>) object.get("data");
        //System.out.println(object.toString());
        assertEquals(2, userList.size());
    }

    @Test
    public void generateOtpTest() {

        User mockUser = new User("id1", 10L,
                "9999999992", "demo-app",
                2000 , "+91");

        OTPGenerateDto dto = new OTPGenerateDto();
        dto.setMobileNumber("9999999993");
        dto.setCountryCode("+91");

        SMSDto smsDto = new SMSDto();
        smsDto.setRequest_id("1");
        smsDto.setSuccess(true);

        ResponseEntity<SMSDto> responseEntity = new ResponseEntity<SMSDto>(smsDto,HttpStatus.OK);
        RestClient mockRestClient = Mockito.mock(RestClient.class);

        when(mockRestClient.sendOTPSMS( "+91", "9940191843", 1234)).thenReturn(responseEntity);
        when(repository.save(Mockito.any(User.class))).thenReturn("id1");

        Map<String, Object> map = (Map<String, Object>) service.generateOTP(dto, "demo-app").getBody();
        System.out.println("test " + map.toString());
        OTPResponseDto response = (OTPResponseDto) map.get("data");
        assertEquals(response.getId() , "id1");
    }

    @Test
    public void restClientTest()  {
        SMSDto smsDto = new SMSDto();
        smsDto.setRequest_id("1");
        smsDto.setSuccess(true);

        ResponseEntity<SMSDto> responseEntity = new ResponseEntity<>(smsDto, HttpStatus.OK);

        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        when(restTemplateMock.exchange(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(SMSDto.class)
        )).thenReturn(responseEntity);
        ResponseEntity<SMSDto> response = restClient.sendOTPSMS("+91", "1234567890", 1234);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
