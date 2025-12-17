package com.example.user_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionLogRepo extends JpaRepository<TransactionLog,Long> {
   Optional<TransactionLog> findByAcknowledgementId(String acknowledgementId);
}
