package com.example.user_service.controller;


import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.service.TransactionService;
import com.example.user_service.service.UserLogInService;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLogInService userLogInService;
    private final TransactionService transactionService;

    @Operation(summary = "Create User")
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest request, @Parameter(hidden = true) @RequestHeader Map<String, Object> header,
                                        HttpServletRequest httpRequest) {
        transactionService.processRequest(request, "CREATE");
        var response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUserDetails(@Valid @RequestBody LoginRequest request,
                                            final HttpServletRequest httpRequest) {

        transactionService.processRequest(request, "LOGIN");
        var response = userLogInService.userLogin(request);
        return ResponseEntity.ok(response);
    }
}
