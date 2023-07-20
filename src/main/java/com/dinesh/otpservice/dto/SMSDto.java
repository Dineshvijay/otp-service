package com.dinesh.otpservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSDto {
    @JsonProperty("return")
    private boolean success;
    private String request_id;
    //private ArrayList<String> message;
}
