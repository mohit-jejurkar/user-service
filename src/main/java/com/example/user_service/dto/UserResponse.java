package com.example.user_service.dto;

import com.example.user_service.common.Constant;

import java.time.LocalDateTime;

public record UserResponse(String message,
                           String acknowledgement,
                           LocalDateTime timestamp,
                           String status) {


    public static UserResponse successResponse(String ackId, String message,String status) {
        return new UserResponse(message, ackId, LocalDateTime.now(), status);
    }

    public static UserResponse failure(String ackId, String message, String status) {
        return new UserResponse(message, ackId, LocalDateTime.now(), status);
    }

}
