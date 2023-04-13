package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.dto.AccountIdProjection;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.repository.AccountFindIdsRepository;
import com.example.bankapplication.repository.AccountRepository;
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
    @Override
    public Collection<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status) {
        log.info("UUID: " + productId + ", Status: " + status);
        return findIdsRepository.findAccountsByProductIdAndStatus(productId, status);
    }
}
