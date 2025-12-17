package com.example.user_service.service.serviceImpl;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.dao.User;
import com.example.user_service.dao.UserRepo;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.UserLogInService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLogInService {


    private final UserRepo repo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse userLogin(LoginRequest request) {

        log.info("inside User Login Service for acknowledgement Id:{}", request.getAcknowledgementId());

        try {

            Optional<User> byEmailId = repo.findByEmailId(request.getEmailId());
            if (byEmailId.isEmpty()) {
                throw new CustomException("user doesn't exists", 404, request.getAcknowledgementId());
            }

            return byEmailId.filter(u -> passwordEncoder.matches(request.getPassword(), byEmailId.get().getPassword()))
                    .map(u -> new UserResponse("Success", request.getAcknowledgementId(), LocalDateTime.now(), "COMPLETED"))
                    .orElseThrow(() -> new CustomException("invalid password", 401, request.getAcknowledgementId()));


        } catch (DataAccessException e) {
            log.error("exception occured in user login service for ackId:{}, with message:{}", request.getAcknowledgementId(), e.getMessage());
            throw new CustomException("Internal Server Error", 500, request.getAcknowledgementId());
        }
    }
}
