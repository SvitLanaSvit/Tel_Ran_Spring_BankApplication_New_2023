package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAll();
    Optional<Transaction> findTransactionById(UUID id);
}
