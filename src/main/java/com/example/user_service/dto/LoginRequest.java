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

    private String password;

    private String acknowledgementId = UUID.randomUUID().toString();

    private String emailId;




    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
        this.emailId = username; // ✅ set same value to emailId
    }
}
