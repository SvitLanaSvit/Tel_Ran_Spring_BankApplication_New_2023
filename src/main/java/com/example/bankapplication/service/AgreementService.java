package com.example.bankapplication.service;

import com.example.bankapplication.dto.*;

import java.util.List;
import java.util.UUID;

public interface AgreementService {
    AgreementListDTO getAll();

    AgreementDTO getAgreementById(UUID id);

    AgreementDTO createAgreement(CreateAgreementDTO createAgreementDTO);

    AgreementDTO editAgreementById(UUID id, CreateAgreementDTO dto);

    void deleteAgreementById(UUID id);

    List<AgreementIdDTO> findAgreementsByManagerId(UUID managerId);

    List<AgreementIdDTO> findAgreementByClientId(UUID clientId);
}
