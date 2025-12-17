package com.example.user_service.dto;

import com.example.user_service.service.BaseRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest implements BaseRequest {

    private String acknowledgementId = UUID.randomUUID().toString();

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "lastname is required")
    private String lastname;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp="^[6-9]\\d{9}$", message="Invalid mobile number")
    private String mobileNo;

    @NotBlank(message = "Address is required")
    private String address;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String emailId;

    @NotBlank(message = "Password is required")
    private String password;




}
