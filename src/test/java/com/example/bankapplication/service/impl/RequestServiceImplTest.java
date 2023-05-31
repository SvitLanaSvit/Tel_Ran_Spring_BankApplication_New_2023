package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.*;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.repository.*;
import com.example.bankapplication.service.RequestService;
import com.example.bankapplication.util.DTOCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for RequestServiceImpl")
class RequestServiceImplTest {

    @Mock
    private AccountFindIdsRepository accountFindIdsRepository;

    @Mock
    private AgreementFindIdsRepository agreementFindIdsRepository;

    @Mock
    private ClientFindRepository clientFindRepository;

    @Mock
    private ManagerFindRepository managerFindRepository;

    @Mock
    private ProductInfoRepository productInfoRepository;

    private RequestService requestService;

    private AccountIdDTO accountIdDTO;
    private List<AccountIdDTO> accountIdDTOList;
    private UUID uuid;
    private ProductStatus status;
    private AgreementIdDTO agreementIdDTO;
    private List<AgreementIdDTO> agreementIdDTOList;
    private ClientInfoDTO clientInfoDTO;
    private List<ClientInfoDTO> clientInfoDTOList;
    private ManagerInfoDTO managerInfoDTO;
    private List<ManagerInfoDTO> managerInfoDTOList;
    private ProductDTO productDTO;
    private List<ProductDTO> productDTOList;

    @BeforeEach
    void setUp() {
        requestService = new RequestServiceImpl(
                accountFindIdsRepository,
                agreementFindIdsRepository,
                clientFindRepository,
                managerFindRepository,
                productInfoRepository);

        accountIdDTO = DTOCreator.getAccountIdDTO();
        accountIdDTOList = new ArrayList<>(List.of(accountIdDTO));
        uuid = UUID.randomUUID();
        status = ProductStatus.ACTIVE;

        agreementIdDTO = DTOCreator.getAgreementIdDTO();
        agreementIdDTOList = new ArrayList<>(List.of(agreementIdDTO));

        clientInfoDTO = DTOCreator.getClientInfoDTO();
        clientInfoDTOList = new ArrayList<>(List.of(clientInfoDTO));

        managerInfoDTO = DTOCreator.getManagerInfoDTO();
        managerInfoDTOList = new ArrayList<>(List.of(managerInfoDTO));

        productDTO = DTOCreator.getProductDTO();
        productDTOList = new ArrayList<>(List.of(productDTO));
    }

    @Test
    void testFindAccountsByProductIdAndStatus() {
        when(accountFindIdsRepository.findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class)))
                .thenReturn(accountIdDTOList);

        List<AccountIdDTO> actualAccountIdDTOList = requestService
                .findAccountsByProductIdAndStatus(uuid, status).stream().toList();
        assertEquals(accountIdDTOList.size(), actualAccountIdDTOList.size());
        for (int i = 0; i < accountIdDTOList.size(); i++) {
            assertEquals(accountIdDTOList.get(i).getId(), actualAccountIdDTOList.get(i).getId());
        }
        verify(accountFindIdsRepository, times(1))
                .findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class));
    }

    @Test
    void testFindAgreementsByManagerId() {
        when(agreementFindIdsRepository.findAgreementsByManagerId(any(UUID.class))).thenReturn(agreementIdDTOList);

        List<AgreementIdDTO> actualAgreementIdDTOList = requestService
                .findAgreementsByManagerId(uuid).stream().toList();
        compareAgreementIdDTOList(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementFindIdsRepository, times(1)).findAgreementsByManagerId(any(UUID.class));
    }

    @Test
    void testFindAgreementByClientId() {
        when(agreementFindIdsRepository.findAgreementByClientId(any(UUID.class)))
                .thenReturn(agreementIdDTOList);

        List<AgreementIdDTO> actualAgreementIdDTOList = requestService
                .findAgreementByClientId(uuid).stream().toList();
        compareAgreementIdDTOList(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementFindIdsRepository, times(1)).findAgreementByClientId(any(UUID.class));
    }

    @Test
    void testFindClientsWhereBalanceMoreThan() {
        when(clientFindRepository.findClientWhereBalanceMoreThan(any(Double.class)))
                .thenReturn(clientInfoDTOList);

        List<ClientInfoDTO> actualClientInfoDTOList = requestService
                .findClientsWhereBalanceMoreThan(any(Double.class)).stream().toList();
        assertEquals(clientInfoDTOList.size(), actualClientInfoDTOList.size());
        for (int i = 0; i < clientInfoDTOList.size(); i++) {
            assertEquals(clientInfoDTOList.get(i), actualClientInfoDTOList.get(i));
        }
        verify(clientFindRepository, times(1)).findClientWhereBalanceMoreThan(any(Double.class));
    }

    @Test
    void testFindAllManagersSortedByProductQuantity() {
        when(managerFindRepository.findAllManagersSortedByProductQuantity()).thenReturn(managerInfoDTOList);

        List<ManagerInfoDTO> actualManagerInfoDTOList = requestService
                .findAllManagersSortedByProductQuantity().stream().toList();
        assertEquals(managerInfoDTOList.size(), actualManagerInfoDTOList.size());
        for (int i = 0; i < managerInfoDTOList.size(); i++) {
            assertEquals(managerInfoDTOList.get(i), actualManagerInfoDTOList.get(i));
        }
        verify(managerFindRepository, times(1)).findAllManagersSortedByProductQuantity();
    }

    @Test
    void testFindAllChangedProducts() {
        when(productInfoRepository.findAllChangedProducts()).thenReturn(productDTOList);

        List<ProductDTO> actualProductDTOList = requestService
                .findAllChangedProducts().stream().toList();
        assertEquals(productDTOList.size(), actualProductDTOList.size());
        for (int i = 0; i < productDTOList.size(); i++) {
            assertEquals(productDTOList.get(i), actualProductDTOList.get(i));
        }
        verify(productInfoRepository, times(1)).findAllChangedProducts();
    }

    private void compareAgreementIdDTOList(List<AgreementIdDTO> expextedList, List<AgreementIdDTO> actualList) {
        assertEquals(expextedList.size(), actualList.size());
        for (int i = 0; i < expextedList.size(); i++) {
            assertEquals(expextedList.get(i).getId(), actualList.get(i).getId());
        }
    }
}