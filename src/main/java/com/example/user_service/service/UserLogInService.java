package com.example.user_service.service;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserLogInService {
    public UserResponse userLogin(LoginRequest request);
}
