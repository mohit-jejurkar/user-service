package com.example.user_service.service;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.common.Constant;
import com.example.user_service.dao.User;
import com.example.user_service.dao.UserRepo;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.serviceImpl.UserLoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserLoginServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    private LoginRequest loginRequest;

    private User user;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmailId("test@gmail.com");
        loginRequest.setPassword("1234");

        user = new User();
        user.setEmailId("test@gmail.com");
        user.setPassword("1234");
    }

    @Test
    void userLogin_success() {

        when(userRepo.findByEmailId(loginRequest.getEmailId()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(true);
        UserResponse response = userLoginService.userLogin(loginRequest);

        assertEquals("COMPLETED", response.status());

        verify(transactionService).completeRequest(eq(loginRequest), any());
    }

    @Test
    void userLogin_userNotFound() {

        when(userRepo.findByEmailId(any()))
                .thenReturn(Optional.empty());

        CustomException ex = assertThrows(
                CustomException.class,
                () -> userLoginService.userLogin(loginRequest)
        );

        assertEquals(404, ex.getStatus());
    }

    @Test
    void userLogin_invalidPassword() {

        when(userRepo.findByEmailId(loginRequest.getEmailId())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(user.getPassword(),loginRequest.getPassword())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> userLoginService.userLogin(loginRequest));

        assertEquals(401, exception.getStatus());
    }


}
