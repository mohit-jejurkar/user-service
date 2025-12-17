package com.example.user_service.ExceptionUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Validation Failed");
        errorResponse.put("errors", fieldErrors);
        return errorResponse;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex, HttpServletRequest request) {

        log.error("error at {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(Map.of("status", ex.getStatus(), "error", ex.getMessage(), "path", request.getRequestURI(), "timeStamp", LocalDateTime.now(), "acknowledgementId", ex.getAcknowledgementId()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(CustomException ex, HttpServletRequest request) {

        log.error("error at {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(Map.of("status", ex.getStatus(), "error", ex.getMessage(), "path", request.getRequestURI(), "timeStamp", LocalDateTime.now(), "acknowledgementId", ex.getAcknowledgementId()));
    }

/*    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, HttpServletRequest request) {

        log.error("Unhandled error at {}", request.getRequestURI(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", 500, "error", ex.getMessage(), "path", request.getRequestURI(), "timeStamp", LocalDateTime.now()));
    }*/

}
