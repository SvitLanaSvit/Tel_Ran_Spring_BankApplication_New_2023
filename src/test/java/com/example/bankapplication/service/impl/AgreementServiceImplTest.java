package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementIdDTO;
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
import com.example.bankapplication.service.exception.NegativeDataException;
import com.example.bankapplication.service.exception.ProductNotFoundException;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    private List<AgreementIdDTO> agreementIdDTOList;

    @BeforeEach
    void setUp() {
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
        agreementIdDTOList = new ArrayList<>(List.of(DTOCreator.getAgreementIdDTO()));
    }

    @Test
    @DisplayName("Positive test.")
    void testGetAll() {
        AgreementListDTO expectedListDTO = new AgreementListDTO(agreementDTOList);

        when(agreementRepository.findAll()).thenReturn(agreementList);

        AgreementListDTO actualListDTO = agreementService.getAll();
        assertEquals(expectedListDTO.getAgreementDTOList().size(), actualListDTO.getAgreementDTOList().size());
        assertEquals(actualListDTO.getAgreementDTOList().get(0).getId(), expectedListDTO.getAgreementDTOList().get(0).getId());
    }

    @Test
    @DisplayName("Positive test.")
    void testGetAgreementById() {
        AgreementDTO expectedAgreementDTO = DTOCreator.getAgreementDTO();

        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.of(agreement));
        var actualAgreementDTO = agreementService.getAgreementById(uuid);
        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        verify(agreementRepository, times(1)).findAgreementById(any(UUID.class));
    }

    @Test
    @DisplayName("Positive test.")
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
    @DisplayName("Positive test.")
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
        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
        verify(productRepository, times(1)).findProductById(any(UUID.class));

        assertEquals(expectedAgreementDTO.getId(), actualAgreementDTO.getId());
        assertEquals(expectedAgreementDTO.getProductId(), actualAgreementDTO.getProductId());
        assertEquals(expectedAgreementDTO.getAccountId(), actualAgreementDTO.getAccountId());
    }

    @Test
    @DisplayName("Positive test.")
    void testDeleteAgreementById() {
        when(agreementRepository.findAgreementById(any(UUID.class)))
                .thenReturn(Optional.of(agreement));

        agreementService.deleteAgreementById(uuid);

        verify(agreementRepository, times(1)).deleteById(uuid);
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    @DisplayName("Negative test. Not found manager`s Id.")
    public void editAgreementById_shouldThrowExceptionWhenManagerNotFound() {
        CreateAgreementDTO dto = DTOCreator.getAgreementToCreate();

        when(agreementRepository.findAgreementById(uuid)).thenReturn(Optional.empty());
        assertThrows(AgreementNotFoundException.class, () -> agreementService.editAgreementById(uuid, dto));
    }

    @Test
    @DisplayName("Negative test. Not found account`s Id.")
    public void testEditAgreementWithNonExistingAccountId() {
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.ofNullable(agreement));
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> agreementService.editAgreementById(uuid, createAgreementDTO));
        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test. Not found product`s Id.")
    public void testEditAgreementWithNonExistingProductId() {
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.ofNullable(agreement));
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.of(account));

        assertThrows(ProductNotFoundException.class, () -> agreementService.editAgreementById(uuid, createAgreementDTO));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test. Not found agreement`s Id.")
    public void testDeleteNonExistingAgreementById() {
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AgreementNotFoundException.class, () -> agreementService.deleteAgreementById(uuid));
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    @DisplayName("Negative test. Not found agreement`s Id.")
    public void testGetAgreementByNonExistingId() {
        when(agreementRepository.findAgreementById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AgreementNotFoundException.class, () -> agreementService.getAgreementById(uuid));
        verify(agreementRepository, times(1)).findAgreementById(uuid);
    }

    @Test
    @DisplayName("Negative test. Not found product`s Id.")
    public void testCreateAgreementWithNullProductId() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> agreementService.createAgreement(createAgreementDTO));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test. Not found account`s Id.")
    public void testCreateAgreementWithNullAccountId() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> agreementService.createAgreement(createAgreementDTO));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    @DisplayName("Positive test")
    void testFindAgreementsByManagerId() {
        when(agreementRepository.findAgreementsByManagerId(any(UUID.class))).thenReturn(agreementIdDTOList);

        List<AgreementIdDTO> actualAgreementIdDTOList = agreementService
                .findAgreementsByManagerId(uuid).stream().toList();
        compareAgreementIdDTOList(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementRepository, times(1)).findAgreementsByManagerId(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test")
    void testEmptyAgreementIdListDTOFindByManagerId() {
        when(agreementRepository.findAgreementsByManagerId(any(UUID.class))).thenReturn(Collections.emptyList());

        assertThrows(NullPointerException.class,
                () -> agreementService.findAgreementsByManagerId(uuid));
        verify(agreementRepository).findAgreementsByManagerId(any(UUID.class));
    }

    @Test
    @DisplayName("Positive test")
    void testFindAgreementByClientId() {
        when(agreementRepository.findAgreementsByClientId(any(UUID.class)))
                .thenReturn(agreementIdDTOList);

        List<AgreementIdDTO> actualAgreementIdDTOList = agreementService
                .findAgreementByClientId(uuid).stream().toList();
        compareAgreementIdDTOList(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementRepository, times(1)).findAgreementsByClientId(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test")
    void testEmptyAgreementIdListDTOFindByClientId() {
        when(agreementRepository.findAgreementsByClientId(any(UUID.class))).thenReturn(Collections.emptyList());

        assertThrows(NullPointerException.class,
                () -> agreementService.findAgreementByClientId(uuid));
        verify(agreementRepository).findAgreementsByClientId(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test")
    void testCreateAgreementWithNegativeInterestRateThrowNegativeDataException() {
        CreateAgreementDTO createAgreementDTONegativeInterestRate = DTOCreator.getAgreementToCreate();
        createAgreementDTONegativeInterestRate.setInterestRate("-1");

        assertThrows(NegativeDataException.class,
                () -> agreementService.createAgreement(createAgreementDTONegativeInterestRate));
    }

    @Test
    @DisplayName("Negative test")
    void testCreateAgreementWithNegativeSumThrowNegativeDataException() {
        CreateAgreementDTO createAgreementDTONegativeInterestRate = DTOCreator.getAgreementToCreate();
        createAgreementDTONegativeInterestRate.setSum("-1");

        assertThrows(NegativeDataException.class,
                () -> agreementService.createAgreement(createAgreementDTONegativeInterestRate));
    }

    @Test
    @DisplayName("Negative test")
    void testEditAgreementWithNegativeInterestRateThrowNegativeDataException() {
        CreateAgreementDTO createAgreementDTONegativeInterestRate = DTOCreator.getAgreementToCreate();
        createAgreementDTONegativeInterestRate.setInterestRate("-1");

        assertThrows(NegativeDataException.class,
                () -> agreementService.editAgreementById(uuid, createAgreementDTONegativeInterestRate));
    }

    @Test
    @DisplayName("Negative test")
    void testEditAgreementWithNegativeSumThrowNegativeDataException() {
        CreateAgreementDTO createAgreementDTONegativeInterestRate = DTOCreator.getAgreementToCreate();
        createAgreementDTONegativeInterestRate.setSum("-1");

        assertThrows(NegativeDataException.class,
                () -> agreementService.editAgreementById(uuid, createAgreementDTONegativeInterestRate));
    }

    private void compareAgreementIdDTOList(List<AgreementIdDTO> expextedList, List<AgreementIdDTO> actualList) {
        assertEquals(expextedList.size(), actualList.size());
        for (int i = 0; i < expextedList.size(); i++) {
            assertEquals(expextedList.get(i).getId(), actualList.get(i).getId());
        }
    }
}