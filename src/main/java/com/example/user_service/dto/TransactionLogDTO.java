package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLogDTO implements Serializable {
    private String acknowledgementId;
    private String emailId;
    private String status;
    private String requestPayload;
    private String responsePayload;
    private Date createdAt;


}
