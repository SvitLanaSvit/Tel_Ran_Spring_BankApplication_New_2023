package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgreementRepository extends JpaRepository<Agreement, UUID> {
    List<Agreement> findAll();
}
