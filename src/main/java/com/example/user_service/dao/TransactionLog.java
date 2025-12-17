package com.example.user_service.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transaction_logs")
public class TransactionLog {


    @Id
    private String acknowledgementId;

    private String emailId;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String responsePayload;

    private String status;
    private String errorMessage;

    private String action;

    private LocalDateTime createdAt = LocalDateTime.now();
}
