package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.*;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.repository.*;
import com.example.bankapplication.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final AccountFindIdsRepository findIdsRepository;
    private final AgreementFindIdsRepository agFindIdsRepository;
    private final ClientFindRepository clientFindRepository;
    private final ManagerFindRepository managerFindRepository;
    private final ProductInfoRepository productInfoRepository;

    @Override
    public Collection<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status) {
        log.info("Find accounts by productId and productStatus");
        log.info("UUID: " + productId + ", Status: " + status);
        return findIdsRepository.findAccountsByProductIdAndStatus(productId, status);
    }

    @Override
    public Collection<AgreementIdDTO> findAgreementsByManagerId(UUID managerId) {
        log.info("Find agreements by managerId {}", managerId);
        return agFindIdsRepository.findAgreementsByManagerId(managerId);
    }

    @Override
    public Collection<AgreementIdDTO> findAgreementByClientId(UUID clientId) {
        log.info("Find agreements by clientId {}", clientId);
        return agFindIdsRepository.findAgreementByClientId(clientId);
    }

    @Override
    public Collection<ClientInfoDTO> findClientsWhereBalanceMoreThan(Double balance) {
        log.info("Find clients with balance more than {}", balance);
        return clientFindRepository.findClientWhereBalanceMoreThan(balance);
    }

    @Override
    public Collection<ManagerInfoDTO> findAllManagersSortedByProductQuantity() {
        log.info("Find managers sorted by product quantity");
        return managerFindRepository.findAllManagersSortedByProductQuantity();
    }

    @Override
    public Collection<ProductDTO> findAllChangedProducts() {
        log.info("Find changed products");
        return productInfoRepository.findAllChangedProducts();
    }
}
