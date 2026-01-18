package com.example.user_service.ExceptionUtils;

import com.example.user_service.common.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put( Constant.Request.timestamp, LocalDateTime.now());
        errorResponse.put( Constant.Request.status, HttpStatus.BAD_REQUEST.value());
        errorResponse.put( Constant.Request.error, "Validation Failed");
        errorResponse.put("errors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);

    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex, HttpServletRequest request) {

        log.error("error at {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(Map.of(Constant.Request.status, ex.getStatus(), Constant.Request.error, ex.getMessage(), Constant.Request.path, request.getRequestURI(), Constant.Request.timestamp, LocalDateTime.now(), Constant.Request.acknowledgement, ex.getAcknowledgementId()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {

        log.error("error at {}: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(Map.of(Constant.Request.status,HttpStatus.INTERNAL_SERVER_ERROR.value(), Constant.Request.error, ex.getMessage(), Constant.Request.path, request.getRequestURI(), Constant.Request.timestamp, LocalDateTime.now()));
    }


}
