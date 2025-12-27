package com.example.user_service.service;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserResponse;
import org.springframework.stereotype.Service;

public interface UserLogInService {
     UserResponse userLogin(LoginRequest request);
}
