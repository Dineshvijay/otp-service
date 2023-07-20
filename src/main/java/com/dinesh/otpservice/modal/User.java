package com.dinesh.otpservice.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "User", timeToLive = 120L)
public class User implements Serializable {
    private static final long serialVersionUID = 9999L;
    @Id
    private String id;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expirationInSeconds;
    private String mobileNumber;
    private String clientId;
    private int otp;
    private String countryCode;

}
