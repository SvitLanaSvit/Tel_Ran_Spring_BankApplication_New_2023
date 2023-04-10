package com.example.bankapplication.service;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;

import java.util.UUID;

public interface AgreementService {
    AgreementListDTO getAll();
    AgreementDTO getAgreementById(UUID id);
    AgreementDTO createAgreement(CreateAgreementDTO createAgreementDTO);
    AgreementDTO editAgreementById(UUID id, CreateAgreementDTO dto);
    void deleteAgreementById(UUID id);
}
