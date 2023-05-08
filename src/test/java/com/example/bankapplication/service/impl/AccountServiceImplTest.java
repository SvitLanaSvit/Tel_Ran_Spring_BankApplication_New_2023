package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.mapper.AccountMapperImpl;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
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
@DisplayName("Test class for AccountServiceImpl")
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    private AccountMapper accountMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp(){
        accountMapper = new AccountMapperImpl();
        accountService = new AccountServiceImpl(accountRepository, accountMapper, clientRepository);
    }

    @Test
    @DisplayName("Positive test. Get account by Id.")
    void testGetAccountById() {
        UUID managerId = UUID.randomUUID();
        Account account = EntityCreator.getAccount(managerId);
        AccountDTO accountDTO = DTOCreator.getAccountDTO();

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        var result = accountService.getAccountById(account.getId());
        assertEquals(result, accountDTO);
        verify(accountRepository, times(1)).findById(account.getId());
    }

    @Test
    @DisplayName("Positive test. Return all list of AccountDTO")
    void testGetAllAccountsStatus() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(EntityCreator.getAccount(UUID.randomUUID()));

        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(DTOCreator.getAccountDTO());

        AccountListDTO accountListDTO = new AccountListDTO(accountDTOList);

        when(accountRepository.getAllByStatus(AccountStatus.ACTIVE)).thenReturn(accountList);

        AccountListDTO result = accountService.getAllAccountsStatus();
        verify(accountRepository).getAllByStatus(AccountStatus.ACTIVE);
        assertEquals(accountListDTO.getAccountDTOList(), result.getAccountDTOList());
    }

    @Test
    void testCreateAccount() {
        UUID id = UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a");
        CreateAccountDTO createAccountDTO = DTOCreator.getAccountToCreate();
        Account expectedAccount = EntityCreator.getAccountAfterDTO(id, createAccountDTO);

        AccountDTO expectedAccountDTO = DTOCreator.getAccountDTO();
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);
        when(clientRepository.findClientById(UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f")))
                .thenReturn(Optional.of(EntityCreator.getClient(UUID.randomUUID())));

        AccountDTO actualAccountDTO = accountService.createAccount(createAccountDTO);
        assertNotNull(actualAccountDTO);
        assertEquals(expectedAccountDTO.getId(), actualAccountDTO.getId());
        assertEquals(expectedAccountDTO.getName(), actualAccountDTO.getName());
        assertEquals(expectedAccountDTO.getType(), actualAccountDTO.getType());
        assertEquals(expectedAccountDTO.getStatus(), actualAccountDTO.getStatus());
        assertEquals(expectedAccountDTO.getCreatedAt(), actualAccountDTO.getCreatedAt());
        assertEquals(expectedAccountDTO.getUpdatedAt(), actualAccountDTO.getUpdatedAt());
        assertEquals(expectedAccountDTO.getClientId(), actualAccountDTO.getClientId());
    }

    @Test
    void testDeleteAccountById() {
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getAccount(UUID.randomUUID())));

        accountService.deleteAccountById(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void testEditAccountById() {
        CreateAccountDTO createAccountDTO = DTOCreator.getAccountToCreate();
        Account account = EntityCreator.getAccount(UUID.randomUUID());
        AccountDTO expectedAccountDTO = DTOCreator.getAccountDTO();

        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(clientRepository.findClientById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getClient(UUID.randomUUID())));

        AccountDTO actualAccountDTO = accountService.editAccountById(UUID.randomUUID(), createAccountDTO);

        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
        verify(accountRepository, times(1)).save(account);

        assertEquals(expectedAccountDTO.getId(), actualAccountDTO.getId());
        assertEquals(expectedAccountDTO.getName(), actualAccountDTO.getName());
    }

    @Test
    void getAll() {
        List<Account> accountList = new ArrayList<>(List.of(EntityCreator.getAccount(UUID.randomUUID())));
        List<AccountDTO> accountDTOList = new ArrayList<>(List.of(DTOCreator.getAccountDTO()));
        AccountListDTO expectedListDTO = new AccountListDTO(accountDTOList);

        when(accountRepository.findAll()).thenReturn(accountList);

        AccountListDTO actualListDTO = accountService.getAll();
        assertEquals(actualListDTO.getAccountDTOList(), expectedListDTO.getAccountDTOList());
    }

    @Test
    @DisplayName("Negative test. Not found account by Id.")
    public void editAccountById_shouldThrowExceptionWhenManagerNotFound() {
        UUID id = UUID.randomUUID();
        CreateAccountDTO dto = DTOCreator.getAccountToCreate();

        when(accountRepository.findAccountById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.editAccountById(id, dto));
    }
}