package com.example.user_service.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponse(String message,
                           String acknowledgement,
                           LocalDateTime timestamp,
                           String status) {
}
