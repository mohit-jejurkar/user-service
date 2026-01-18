package com.example.user_service.service;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.common.Constant;
import com.example.user_service.common.UserMapper;
import com.example.user_service.dao.User;
import com.example.user_service.dao.UserRepo;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TransactionService transactionService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper mapper;

    private UserRequest userRequest;

    private User user;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setEmailId("abc@gmail.com");
        userRequest.setPassword("pass");
        userRequest.setName("abc");
        userRequest.setAddress("asdasdas");
        userRequest.setMobileNo("1234567890");
        userRequest.setLastname("safasfas");
        userRequest.setAcknowledgementId("sadasdasd");

        user = new User();

    }

    @Test
    void createUser_success() {


        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        UserResponse response = userService.createUser(userRequest);

        assertEquals(Constant.Transaction.success, response.status());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void createUser_duplicateEmail() {

        when(userRepo.save(any()))
                .thenThrow(DataIntegrityViolationException.class);


        when(mapper.toEntity(any())).thenReturn(user);

        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        assertThrows(CustomException.class,
                () -> userService.createUser(userRequest));
    }


}
