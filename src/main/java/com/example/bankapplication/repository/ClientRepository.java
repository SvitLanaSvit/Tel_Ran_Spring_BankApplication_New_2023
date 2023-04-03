package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findClientById(UUID id);
    Optional<Client> findClientByTaxCode(String taxCode);
    List<Client> getAllByStatus(ClientStatus status);
}
