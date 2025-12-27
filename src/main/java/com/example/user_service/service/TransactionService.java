package com.example.user_service.service;

import com.example.user_service.dto.UserResponse;

public interface TransactionService {
    void processRequest(BaseRequest request, String action);

    void completeRequest(BaseRequest request, UserResponse response);
}
