package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.Agreement;
import com.example.bankapplication.mapper.AgreementMapper;
import com.example.bankapplication.mapper.AgreementMapperImpl;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.AgreementRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.AgreementService;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for AgreementServiceImpl")
class AgreementServiceImplTest {

    @Mock
    private AgreementRepository agreementRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AccountRepository accountRepository;

    private AgreementMapper agreementMapper;

    private AgreementService agreementService;

    @BeforeEach
    void setUp(){
        agreementMapper = new AgreementMapperImpl();
        agreementService = new AgreementServiceImpl(agreementMapper, agreementRepository, productRepository, accountRepository);
    }

    @Test
    void testGetAll() {
        List<Agreement> agreementList = new ArrayList<>(List.of(EntityCreator.getAgreement(UUID.randomUUID())));
        List<AgreementDTO> agreementDTOList = new ArrayList<>(List.of(DTOCreator.getAgreementDTO()));
        AgreementListDTO expectedListDTO = new AgreementListDTO(agreementDTOList);

        when(agreementRepository.findAll()).thenReturn(agreementList);

        AgreementListDTO actualListDTO = agreementService.getAll();
        assertEquals(expectedListDTO.getAgreementDTOList().size(), actualListDTO.getAgreementDTOList().size());
        assertEquals(actualListDTO.getAgreementDTOList().get(0).getId(), expectedListDTO.getAgreementDTOList().get(0).getId());
    }

    @Test
    void testGetAgreementById() {
        Agreement agreement = EntityCreator.getAgreement(UUID.randomUUID());
        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();

        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.of(agreement));
        var actualAgreementDTO = agreementService.getAgreementById(UUID.randomUUID());
        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        verify(agreementRepository, times(1)).findAgreementById(any(UUID.class));
    }

    @Test
    void testCreateAgreement() {
        UUID agreementId = UUID.fromString("9c149059-3d2a-4741-9073-a05364ecb6cf");
        CreateAgreementDTO createAgreementDTO = DTOCreator.getAgreementToCreate();
        Agreement expectedAgreement = EntityCreator.getAgreementAfterDTO(agreementId, createAgreementDTO);

        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();
        when(agreementRepository.save(any(Agreement.class))).thenReturn(expectedAgreement);
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getProduct(UUID.randomUUID())));
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getAccount(UUID.randomUUID())));

        AgreementDTO actualAgreementDTO = agreementService.createAgreement(createAgreementDTO);
        assertNotNull(actualAgreementDTO);
        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        assertEquals(expectedAgreementDTO.getAccountId(), actualAgreementDTO.getAccountId());
        assertEquals(expectedAgreementDTO.getProductId(), actualAgreementDTO.getProductId());
    }

    @Test
    void editAgreementById() {
        CreateAgreementDTO createAgreementDTO = DTOCreator.getAgreementToCreate();
        Agreement agreement = EntityCreator.getAgreement(UUID.randomUUID());
        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();

        when(agreementRepository.findAgreementById(any(UUID.class)))
                .thenReturn(Optional.of(agreement));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getAccount(UUID.randomUUID())));
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getProduct(UUID.randomUUID())));

        AgreementDTO actualAgreementDTO = agreementService.editAgreementById(UUID.randomUUID(), createAgreementDTO);

        verify(agreementRepository, times(1)).findAgreementById(any(UUID.class));
        verify(agreementRepository, times(1)).save(any(Agreement.class));

        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        assertEquals(expectedAgreementDTO.getProductId(), actualAgreementDTO.getProductId());
        assertEquals(expectedAgreementDTO.getAccountId(), actualAgreementDTO.getAccountId());
    }

    @Test
    void deleteAgreementById() {
        UUID agreementId = UUID.randomUUID();
        when(agreementRepository.findAgreementById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getAgreement(UUID.randomUUID())));

        agreementService.deleteAgreementById(agreementId);

        verify(agreementRepository, times(1)).deleteById(agreementId);
    }
}