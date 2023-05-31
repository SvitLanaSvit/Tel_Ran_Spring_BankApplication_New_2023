package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import com.example.bankapplication.entity.enums.CurrencyCode;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for AccountRepository")
class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    private List<Account> accounts;
    private Client client;

    @BeforeEach
    void setUp() {
        client = mock(Client.class);
        accounts = new ArrayList<>(List.of(
                new Account(
                        UUID.randomUUID(), "account1", AccountType.STUDENT, AccountStatus.ACTIVE, 50,
                        CurrencyCode.USD, null, null, client)
        ));
    }


    @Test
    @DisplayName("Positive test. Find account by id.")
    void testFindAccountById() {
        Account account = EntityCreator.getAccount(UUID.randomUUID());

        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountRepository.findAccountById(account.getId());
        assertTrue(foundAccount.isPresent());
        assertEquals(account, foundAccount.get());

        verify(accountRepository).findAccountById(account.getId());
    }

    @Test
    @DisplayName("Positive test. Find all accounts by status.")
    void testGetAllByStatus() {
        when(accountRepository.getAllByStatus(AccountStatus.ACTIVE)).thenReturn(accounts);
        List<Account> foundAccounts = accountRepository.getAllByStatus(AccountStatus.ACTIVE);

        assertEquals(accounts.size(), foundAccounts.size());
        verify(accountRepository, times(1)).getAllByStatus(AccountStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all accounts.")
    void testFindAll() {
        when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> foundAccounts = accountRepository.findAll();
        assertEquals(accounts.size(), foundAccounts.size());
        verify(accountRepository, times(1)).findAll();
    }
}