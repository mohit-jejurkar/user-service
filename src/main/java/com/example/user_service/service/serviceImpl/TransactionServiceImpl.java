package com.example.user_service.service.serviceImpl;

import com.example.user_service.dao.TransactionLog;
import com.example.user_service.dao.TransactionLogRepo;
import com.example.user_service.service.BaseRequest;
import com.example.user_service.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionLogRepo transactionLogRepo;


    public void processRequest(BaseRequest request, String action) {
        TransactionLog transactionLogDTO = new TransactionLog();
        transactionLogDTO.setRequestPayload(request.toString());
        transactionLogDTO.setAcknowledgementId(request.getAcknowledgementId());
        transactionLogDTO.setEmailId(request.getEmailId());
        transactionLogDTO.setStatus("IN-PROCESS");
        transactionLogDTO.setAction(action);
        try {
            transactionLogRepo.save(transactionLogDTO);
        } catch (Exception e) {
            log.error("error while saving data into DB for transactionId :{}", request.getAcknowledgementId());
        }
    }

    public void completeRequest(BaseRequest request) {
        Optional<TransactionLog> byAcknowlwdgementId = transactionLogRepo.findByAcknowledgementId(request.getAcknowledgementId());

        TransactionLog transactionLogDTO = byAcknowlwdgementId.orElseThrow(() -> new RuntimeException());
        transactionLogDTO.setStatus("COMPLETED");

        try {
            transactionLogRepo.save(transactionLogDTO);
        } catch (Exception e) {
            log.error("error while saving data into DB for transactionId :{}", request.getAcknowledgementId());
        }
    }

    public void completeRequestWithUserExists(BaseRequest request) {
        Optional<TransactionLog> transactionLog = transactionLogRepo.findByAcknowledgementId(request.getAcknowledgementId());
        TransactionLog transactionLogDTO = new TransactionLog();

        if (transactionLog.isPresent()) {
            setTransactionLogs(transactionLog, transactionLogDTO);
            transactionLogDTO.setStatus("COMPLETED");
            transactionLogDTO.setErrorMessage("USER_EXITS");


        }
        try {
            transactionLogRepo.save(transactionLogDTO);
        } catch (Exception e) {
            log.error("error while saving data into DB for transactionId :{}", request.getAcknowledgementId());
        }
    }

    private void setTransactionLogs(Optional<TransactionLog> log, TransactionLog transactionLogDTO) {
        TransactionLog transactionLog = log.get();
        extracted(transactionLogDTO, transactionLog);
    }

    private void extracted(TransactionLog transactionLogDTO, TransactionLog transactionLog) {
        transactionLogDTO.setAcknowledgementId(transactionLog.getAcknowledgementId());
        transactionLogDTO.setRequestPayload(transactionLog.getRequestPayload());
        transactionLogDTO.setEmailId(transactionLog.getEmailId());
        transactionLogDTO.setAction(transactionLog.getAction());
    }
}
