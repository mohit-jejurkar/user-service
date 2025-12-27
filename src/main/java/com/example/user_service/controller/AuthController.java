package com.example.user_service.controller;

import com.example.user_service.ExceptionUtils.CustomException;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmailId(),
                            request.getPassword())
            );
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), 401, "");
        }

        String token =
                jwtUtil.generateToken(request.getEmailId());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
