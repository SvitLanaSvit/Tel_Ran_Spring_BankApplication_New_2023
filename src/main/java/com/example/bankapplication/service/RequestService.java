package com.example.bankapplication.service;

import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.dto.AgreementIdDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.ManagerInfoDTO;
import com.example.bankapplication.entity.enums.ProductStatus;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.UUID;

public interface RequestService {
    @Transactional
    Collection<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status);

    @Transactional
    Collection<AgreementIdDTO> findAgreementsByManagerId(UUID managerId);

    @Transactional
    Collection<AgreementIdDTO> findAgreementByClientId(UUID clientId);

    @Transactional
    Collection<ClientInfoDTO> findClientsWhereBalanceMoreThan(Double balance);

    @Transactional
    Collection<ManagerInfoDTO> findAllManagersSortedByProductQuantity();
}
