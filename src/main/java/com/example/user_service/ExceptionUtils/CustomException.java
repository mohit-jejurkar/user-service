package com.example.user_service.ExceptionUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final int status;
    private final String acknowledgementId;

    public CustomException(String message, int status, String acknowledgementId) {
        super(message);
        this.status = status;
        this.acknowledgementId = acknowledgementId;
    }
}
