package com.example.user_service.dto;

import com.example.user_service.service.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequest implements BaseRequest {

    @NotBlank(message = "username can not be blank")
    private String username;

    @NotBlank(message = "password can not be blank")
    private String password;

    private String acknowledgementId = UUID.randomUUID().toString();

    @NotBlank(message = "emailId can not be blank")
    private String emailId;

}
