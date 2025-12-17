package com.example.user_service.controller;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword())
            );
        }catch (Exception e){
           log.error( "    sfsdsdfsdfsfasf"+e.getStackTrace());
        }

        String token =
                jwtUtil.generateToken(request.getEmailId());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
