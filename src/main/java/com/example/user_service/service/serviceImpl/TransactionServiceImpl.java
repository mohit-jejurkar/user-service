package com.example.user_service.service.serviceImpl;

import com.example.user_service.common.Constant;
import com.example.user_service.dao.TransactionLog;
import com.example.user_service.dao.TransactionLogRepo;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.BaseRequest;
import com.example.user_service.service.TransactionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionLogRepo transactionLogRepo;

    private final ObjectMapper objectMapper;


    @Async("transactionExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processRequest(BaseRequest request, String action) {
        TransactionLog transactionLogDTO = new TransactionLog();
        transactionLogDTO.setAcknowledgementId(request.getAcknowledgementId());
        transactionLogDTO.setEmailId(request.getEmailId());
        transactionLogDTO.setStatus(Constant.Transaction.inprocess);
        transactionLogDTO.setAction(action);
        try {
            transactionLogDTO.setRequestPayload(objectMapper.writeValueAsString(request));
            transactionLogRepo.save(transactionLogDTO);
        } catch (Exception e) {
            log.error("error while saving data into DB for transactionId :{}", request.getAcknowledgementId());
        }
    }

    @Async("transactionExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeRequest(BaseRequest request, UserResponse response) {
        Optional<TransactionLog> byAcknowlwdgementId = transactionLogRepo.findByAcknowledgementId(request.getAcknowledgementId());

        TransactionLog transactionLogDTO = byAcknowlwdgementId.orElseThrow(() -> new RuntimeException("Transaction log not found for ackId: " + request.getAcknowledgementId()));
        transactionLogDTO.setStatus(Constant.Transaction.completed);

        try {
            if (!response.status().equalsIgnoreCase(Constant.Transaction.success)) {
                transactionLogDTO.setErrorMessage(response.message());
            } else {
                transactionLogDTO.setErrorMessage(Constant.Transaction.success);
            }
            transactionLogDTO.setResponsePayload(objectMapper.writeValueAsString(response));
            transactionLogRepo.save(transactionLogDTO);
        } catch (Exception e) {
            log.error("error while saving data into DB for transactionId :{}", request.getAcknowledgementId());
        }
    }
}
