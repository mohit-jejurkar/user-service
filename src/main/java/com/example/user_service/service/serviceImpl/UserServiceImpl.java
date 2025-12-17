package com.example.user_service.service.serviceImpl;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.common.UserMapper;
import com.example.user_service.dao.User;
import com.example.user_service.dao.UserRepo;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.TransactionService;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepo repo;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        try {
            User user = mapper.toEntity(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            User save = repo.save(user);
            transactionService.completeRequest(request);
            return new UserResponse("User Created Successfully", save.getAcknowledgementId(), LocalDateTime.now(), "COMPLETED");
        } catch (DataIntegrityViolationException ex){
            throw new CustomException("Email Address is already present",409,request.getAcknowledgementId());
        }
    }

}
