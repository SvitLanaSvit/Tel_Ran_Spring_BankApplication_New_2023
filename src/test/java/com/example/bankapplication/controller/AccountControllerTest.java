package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.RequestService;
import com.example.bankapplication.util.DTOCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID uuid;
    private CreateAccountDTO createAccountDTO;
    private AccountDTO accountDTO;
    private List<AccountDTO> list;
    private AccountListDTO accountListDTO;
    private AccountIdDTO accountIdDTO;
    private List<AccountIdDTO> accountIdDTOList;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        createAccountDTO = DTOCreator.getAccountToCreate();
        accountDTO = DTOCreator.getAccountDTO();
        list = new ArrayList<>(List.of(DTOCreator.getAccountDTO()));
        accountListDTO = new AccountListDTO(list);
        accountIdDTO = DTOCreator.getAccountIdDTO();
        accountIdDTOList = new ArrayList<>(List.of(accountIdDTO));
    }

    @Test
    void testCreateAccount() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountDTO));

        when(accountService.createAccount(any(CreateAccountDTO.class))).thenReturn(accountDTO);
        System.out.println(createAccountDTO);
        System.out.println(accountDTO);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(accountService, times(1)).createAccount(any(CreateAccountDTO.class));
    }

    @Test
    void testGetAccountById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/account/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(accountService.getAccountById(any(UUID.class))).thenReturn(accountDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AccountDTO actualAccount = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountDTO.class);
        compareDTO(accountDTO, actualAccount);
        verify(accountService, times(1)).getAccountById(any(UUID.class));
    }

    @Test
    void testGetAllAccounts() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/accounts")
                .contentType(MediaType.APPLICATION_JSON);

        when(accountService.getAllAccountsStatus()).thenReturn(accountListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AccountListDTO actualAccountListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AccountListDTO.class);

        compareListDTO(accountListDTO, actualAccountListDTO);
        verify(accountService, times(1)).getAllAccountsStatus();
    }

    @Test
    void testDelete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteAccount/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(accountService, times(1)).deleteAccountById(any(UUID.class));
    }

    @Test
    void testEditAccount() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .put("/auth/editAccount/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAccountDTO));

        when(accountService.editAccountById(any(UUID.class), any(CreateAccountDTO.class))).thenReturn(accountDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AccountDTO actualAccountDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountDTO.class);
        compareDTO(accountDTO, actualAccountDTO);
        verify(accountService, times(1)).editAccountById(any(UUID.class), any(CreateAccountDTO.class));
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/accounts/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(accountService.getAll()).thenReturn(accountListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AccountListDTO actualAccountListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AccountListDTO.class);
        compareListDTO(accountListDTO, actualAccountListDTO);
        verify(accountService, times(1)).getAll();
    }

    @Test
    void testGetAccountIdsByProductIdAndStatus() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAccounts?productId=2ee47e2a-6cfc-4f1d-b0ac-72e6c3ca5abc&status=ACTIVE")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class)))
                .thenReturn(accountIdDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<AccountIdDTO>> typeReference = new TypeReference<>() {
        };
        var actualAccountIdDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), typeReference);
        System.out.println(actualAccountIdDTO.get(0).getId());
        compareListIdDTO(accountIdDTOList, actualAccountIdDTO);
        verify(requestService, times(1))
                .findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class));
    }

    @Test
    void testGetAccountIdsByProductIdAndStatusQuery() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAccountsJPA?id=2ee47e2a-6cfc-4f1d-b0ac-72e6c3ca5abc&status=ACTIVE")
                .contentType(MediaType.APPLICATION_JSON);

        when(accountService.findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class)))
                .thenReturn(accountIdDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<AccountIdDTO>> typeReference = new TypeReference<>() {
        };
        var actualAccountIdDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), typeReference);
        System.out.println(actualAccountIdDTO.get(0).getId());
        compareListIdDTO(accountIdDTOList, actualAccountIdDTO);
        verify(accountService, times(1))
                .findAccountsByProductIdAndStatus(any(UUID.class), any(ProductStatus.class));
    }

    private void compareDTO(AccountDTO expectedDTO, AccountDTO actualDTO) {
        assertAll(
                () -> assertEquals(expectedDTO.getId(), actualDTO.getId()),
                () -> assertEquals(expectedDTO.getName(), actualDTO.getName()),
                () -> assertEquals(expectedDTO.getType(), actualDTO.getType()),
                () -> assertEquals(expectedDTO.getBalance(), actualDTO.getBalance()),
                () -> assertEquals(expectedDTO.getCurrencyCode(), actualDTO.getCurrencyCode()),
                () -> assertEquals(expectedDTO.getClientId(), actualDTO.getClientId())
        );
    }

    private void compareListDTO(AccountListDTO expectedListDTO, AccountListDTO actualListDTO) {
        assertEquals(expectedListDTO.getAccountDTOList().size(), actualListDTO.getAccountDTOList().size());
        for (int i = 0; i < expectedListDTO.getAccountDTOList().size(); i++) {
            compareDTO(expectedListDTO.getAccountDTOList().get(i), actualListDTO.getAccountDTOList().get(i));
        }
    }

    private void compareListIdDTO(List<AccountIdDTO> expectedListIdDTO, List<AccountIdDTO> actualListIdDTO) {
        assertEquals(expectedListIdDTO.size(), actualListIdDTO.size());
        for (int i = 0; i < expectedListIdDTO.size(); i++) {
            assertEquals(expectedListIdDTO.get(i).getId(), actualListIdDTO.get(i).getId());
        }
    }
}