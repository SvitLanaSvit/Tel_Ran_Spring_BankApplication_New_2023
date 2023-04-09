package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.mapper.AgreementMapper;
import com.example.bankapplication.repository.AgreementRepository;
import com.example.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementServiceImpl implements AgreementService {
    private final AgreementMapper agreementMapper;
    private final AgreementRepository agreementRepository;
    @Override
    public AgreementListDTO getAll() {
        log.info("Get all agreements");
        return new AgreementListDTO(agreementMapper.agreementsToAgreementsDTO(agreementRepository.findAll()));
    }
}
