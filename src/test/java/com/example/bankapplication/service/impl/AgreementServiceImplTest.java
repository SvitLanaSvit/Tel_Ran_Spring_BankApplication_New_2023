package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Agreement;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.mapper.AgreementMapper;
import com.example.bankapplication.mapper.AgreementMapperImpl;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.AgreementRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.AgreementService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.AgreementNotFoundException;
import com.example.bankapplication.service.exception.ProductNotFoundException;
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

    private UUID uuid;
    private Agreement agreement;
    private List<Agreement> agreementList;
    private AgreementDTO agreementDTO;
    private List<AgreementDTO> agreementDTOList;
    private CreateAgreementDTO createAgreementDTO;
    private Product product;
    private Account account;

    @BeforeEach
    void setUp(){
        agreementMapper = new AgreementMapperImpl();
        agreementService = new AgreementServiceImpl(agreementMapper, agreementRepository, productRepository, accountRepository);
        uuid = UUID.randomUUID();
        agreement = EntityCreator.getAgreement(uuid);
        agreementList = new ArrayList<>(List.of(agreement));
        agreementDTO = DTOCreator.getAgreementDTO();
        agreementDTOList = new ArrayList<>(List.of(agreementDTO));
        createAgreementDTO = DTOCreator.getAgreementToCreate();
        product = EntityCreator.getProduct(uuid);
        account = EntityCreator.getAccount(uuid);

    }

    @Test
    void testGetAll() {
        AgreementListDTO expectedListDTO = new AgreementListDTO(agreementDTOList);

        when(agreementRepository.findAll()).thenReturn(agreementList);

        AgreementListDTO actualListDTO = agreementService.getAll();
        assertEquals(expectedListDTO.getAgreementDTOList().size(), actualListDTO.getAgreementDTOList().size());
        assertEquals(actualListDTO.getAgreementDTOList().get(0).getId(), expectedListDTO.getAgreementDTOList().get(0).getId());
    }

    @Test
    void testGetAgreementById() {
        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();

        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.of(agreement));
        var actualAgreementDTO = agreementService.getAgreementById(uuid);
        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        verify(agreementRepository, times(1)).findAgreementById(any(UUID.class));
    }

    @Test
    void testCreateAgreement() {
        UUID agreementId = UUID.fromString("9c149059-3d2a-4741-9073-a05364ecb6cf");
        Agreement expectedAgreement = EntityCreator.getAgreementAfterDTO(agreementId, createAgreementDTO);

        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();
        when(agreementRepository.save(any(Agreement.class))).thenReturn(expectedAgreement);
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(product));
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(account));

        AgreementDTO actualAgreementDTO = agreementService.createAgreement(createAgreementDTO);
        assertNotNull(actualAgreementDTO);
        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        assertEquals(expectedAgreementDTO.getAccountId(), actualAgreementDTO.getAccountId());
        assertEquals(expectedAgreementDTO.getProductId(), actualAgreementDTO.getProductId());
    }

    @Test
    void testEditAgreementById() {
        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();

        when(agreementRepository.findAgreementById(any(UUID.class)))
                .thenReturn(Optional.of(agreement));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(account));
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(product));

        AgreementDTO actualAgreementDTO = agreementService.editAgreementById(uuid, createAgreementDTO);

        verify(agreementRepository, times(1)).findAgreementById(any(UUID.class));
        verify(accountRepository,times(1)).findAccountById(any(UUID.class));
        verify(productRepository, times(1)).findProductById(any(UUID.class));

        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        assertEquals(expectedAgreementDTO.getProductId(), actualAgreementDTO.getProductId());
        assertEquals(expectedAgreementDTO.getAccountId(), actualAgreementDTO.getAccountId());
    }

    @Test
    void testDeleteAgreementById() {
        when(agreementRepository.findAgreementById(any(UUID.class)))
                .thenReturn(Optional.of(agreement));

        agreementService.deleteAgreementById(uuid);

        verify(agreementRepository, times(1)).deleteById(uuid);
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    @DisplayName("Negative test. Not found agreement by Id.")
    public void editAgreementById_shouldThrowExceptionWhenManagerNotFound() {
        CreateAgreementDTO dto = DTOCreator.getAgreementToCreate();

        when(agreementRepository.findAgreementById(uuid)).thenReturn(Optional.empty());
        assertThrows(AgreementNotFoundException.class, () -> agreementService.editAgreementById(uuid, dto));
    }

    @Test
    public void testEditAgreementWithNonExistingAccountId(){
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.ofNullable(agreement));
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, ()->agreementService.editAgreementById(uuid, createAgreementDTO));
        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
    }

    @Test
    public void testEditAgreementWithNonExistingProductId(){
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.ofNullable(agreement));
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.of(account));

        assertThrows(ProductNotFoundException.class, () -> agreementService.editAgreementById(uuid, createAgreementDTO));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    public void testDeleteNonExistingAgreementById(){
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AgreementNotFoundException.class, () -> agreementService.deleteAgreementById(uuid));
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    public void testGetAgreementByNonExistingId(){
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AgreementNotFoundException.class, () -> agreementService.getAgreementById(uuid));
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    public void testCreateAgreementWithNullProductId(){
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> agreementService.createAgreement(createAgreementDTO));
        verify(productRepository,times(1)).findProductById(any(UUID.class));
    }

    @Test
    public void testCreateAgreementWithNullAccountId(){
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> agreementService.createAgreement(createAgreementDTO));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }
}