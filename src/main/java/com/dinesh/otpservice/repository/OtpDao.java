package com.dinesh.otpservice.repository;

import com.dinesh.otpservice.modal.User;

import java.util.List;
import java.util.Optional;

public interface OtpDao {
    String save(User user);
    User find(String key, String hashKey);
}
