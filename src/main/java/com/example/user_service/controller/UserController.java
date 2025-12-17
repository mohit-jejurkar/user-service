package com.example.user_service.controller;


import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.TransactionService;
import com.example.user_service.service.UserLogInService;
import com.example.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLogInService userLogInService;
    private final TransactionService transactionService;

    @PostMapping(value = "/createUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request,
                                   @RequestHeader Map<String, Object> header,
                                   final HttpServletRequest httpRequest
    ) {
        transactionService.processRequest(request, "CREATE");
        var response = userService.createUser(request);
        return ResponseEntity.created(URI.create("sdfsdfsd")).body(response);
    }

    @PostMapping(value = "auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUserDetails(@Valid @RequestBody LoginRequest request,
                                            final HttpServletRequest httpRequest) {

        transactionService.processRequest(request, "LOGIN");
        var response = userLogInService.userLogin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public String ping() {
        System.out.println("Ping received");
        return "pong";
    }
}
