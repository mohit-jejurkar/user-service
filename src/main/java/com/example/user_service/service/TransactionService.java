package com.example.user_service.service;

public interface TransactionService {
    void processRequest(BaseRequest request, String action);

    void completeRequest(BaseRequest request);

    void completeRequestWithUserExists(BaseRequest request);
}
