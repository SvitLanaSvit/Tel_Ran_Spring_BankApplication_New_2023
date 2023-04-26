package com.example.bankapplication.controller;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.util.EntityCreator;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @MockBean
    SpringLiquibase springLiquibase;

    @MockBean
    DataSource dataSource;

    @MockBean
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountController accountController;

    @Test
    void testCreateAccount() throws Exception {
        Account account = EntityCreator.getAccount(UUID.randomUUID());
        String str = """
                {
                     "id": "88fbc41b-b95d-4ec6-a4e5-6484021f64c1",
                     "name": "MyAccount",
                     "type": "STUDENT",
                     "status": "ACTIVE",
                     "balance": "5000.0",
                     "currencyCode": "USD",
                     "createdAt": "null",
                     "updatedAt": null,
                     "clientId": "34fe63b8-0958-49f3-b0c4-f9f50744a77f"
                 }
                """;

        when(accountRepository.save(any())).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/createAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(str)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("STUDENT")));


    }

    @Test
    void getAccountById() {
    }

    @Test
    void getAllAccounts() {
    }

    @Test
    void delete() {
    }

    @Test
    void editAccount() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAccountIdsByProductIdAndStatus() {
    }
}