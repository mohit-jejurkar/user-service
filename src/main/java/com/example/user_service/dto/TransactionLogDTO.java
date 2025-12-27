package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class TransactionLogDTO implements Serializable {
    private final String acknowledgementId;
    private final String emailId;
    private final String status;
    private String requestPayload;
    private String responsePayload;
    private Date createdAt;


}
