package com.example.user_service.service.serviceImpl;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.common.Constant;
import com.example.user_service.dao.User;
import com.example.user_service.dao.UserRepo;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.TransactionService;
import com.example.user_service.service.UserLogInService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLogInService {


    private final UserRepo repo;

    private final PasswordEncoder passwordEncoder;

    private final TransactionService transactionService;

    @Override
    public UserResponse userLogin(LoginRequest request) {

        log.info("inside User Login Service for acknowledgement Id:{}", request.getAcknowledgementId());

        try {

            Optional<User> byEmailId = repo.findByEmailId(request.getEmailId());
            if (byEmailId.isEmpty()) {
                transactionService.completeRequest(request, UserResponse.failure(request.getAcknowledgementId(), "USER DOESN'T EXITS", Constant.Transaction.error));
                throw new CustomException("User doesn't exist", 404, request.getAcknowledgementId());
            } else if (passwordEncoder.matches(request.getPassword(), byEmailId.get().getPassword())) {
                transactionService.completeRequest(request, UserResponse.successResponse(request.getAcknowledgementId(), "LOGIN SUCCESS",Constant.Request.success));
                return new UserResponse(Constant.Request.success, request.getAcknowledgementId(), LocalDateTime.now(),Constant.Transaction.completed );
            } else {
                transactionService.completeRequest(request, UserResponse.failure(request.getAcknowledgementId(), "INVALID PASSWORD",Constant.Transaction.error));
                throw new CustomException("Invalid Password", 401, request.getAcknowledgementId());
            }

        } catch (DataAccessException e) {
            transactionService.completeRequest(request, UserResponse.failure(request.getAcknowledgementId(), "LOGIN FAILED", Constant.Transaction.error));
            log.error("exception occured in user login service for ackId:{}, with message:{}", request.getAcknowledgementId(), e);
            throw new CustomException("Internal Server Error", 500, request.getAcknowledgementId());
        }
    }
}
