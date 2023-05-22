package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.dto.AccountListDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.mapper.AccountMapperImpl;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.AccountService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.ClientNotFoundException;
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
@DisplayName("Test class for AccountServiceImpl")
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    private AccountMapper accountMapper;
    private AccountService accountService;

    private Account account;
    private AccountDTO accountDTO;
    private List<Account> accountList;
    private List<AccountDTO> accountDTOList;
    private AccountListDTO accountListDTO;
    private CreateAccountDTO createAccountDTO;
    private UUID uuid;
    private Client client;
    private List<AccountIdDTO> accountIdDTOList;

    @BeforeEach
    void setUp(){
        accountMapper = new AccountMapperImpl();
        accountService = new AccountServiceImpl(accountRepository, accountMapper, clientRepository);
        account = EntityCreator.getAccount(UUID.randomUUID());
        accountDTO = DTOCreator.getAccountDTO();
        accountList = new ArrayList<>(List.of(account));
        accountDTOList = new ArrayList<>(List.of(accountDTO));
        accountListDTO = new AccountListDTO(accountDTOList);
        createAccountDTO = DTOCreator.getAccountToCreate();
        uuid = UUID.randomUUID();
        client = EntityCreator.getClient(uuid);
        accountIdDTOList = new ArrayList<>(List.of(DTOCreator.getAccountIdDTO()));
    }

    @Test
    @DisplayName("Positive test. Get account by Id.")
    void testGetAccountById() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        var result = accountService.getAccountById(account.getId());
        assertEquals(result, accountDTO);
        verify(accountRepository, times(1)).findById(account.getId());
    }

    @Test
    @DisplayName("Positive test. Return all list of AccountDTO")
    void testGetAllAccountsStatus() {
        when(accountRepository.getAllByStatus(AccountStatus.ACTIVE)).thenReturn(accountList);

        AccountListDTO result = accountService.getAllAccountsStatus();
        verify(accountRepository).getAllByStatus(AccountStatus.ACTIVE);
        assertEquals(accountListDTO.getAccountDTOList(), result.getAccountDTOList());
    }

    @Test
    void testCreateAccount() {
        UUID id = UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a");
        Account expectedAccount = EntityCreator.getAccountAfterDTO(id, createAccountDTO);

        AccountDTO expectedAccountDTO = accountDTO;
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);
        when(clientRepository.findClientById(UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f")))
                .thenReturn(Optional.of(client));

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
        when(accountRepository.findAccountById(any(UUID.class)))
                .thenReturn(Optional.of(account));

        accountService.deleteAccountById(uuid);
        verify(accountRepository, times(1)).deleteById(uuid);
    }

    @Test
    void testEditAccountById() {
        AccountDTO expectedAccountDTO = accountDTO;

        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO actualAccountDTO = accountService.editAccountById(uuid, createAccountDTO);

        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
        verify(accountRepository, times(1)).save(account);

        assertEquals(expectedAccountDTO.getId(), actualAccountDTO.getId());
        assertEquals(expectedAccountDTO.getName(), actualAccountDTO.getName());
    }

    @Test
    void testGetAll() {
        AccountListDTO expectedListDTO = new AccountListDTO(accountDTOList);

        when(accountRepository.findAll()).thenReturn(accountList);

        AccountListDTO actualListDTO = accountService.getAll();
        assertEquals(actualListDTO.getAccountDTOList(), expectedListDTO.getAccountDTOList());
    }

    @Test
    @DisplayName("Negative test. Not found account by Id.")
    public void testEditNonExistingAccountById() {
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.editAccountById(uuid, createAccountDTO));
    }

//    @Test
//    @DisplayName("Negative test: Edit account with non-existing clientId")
//    void testEditAccountWithNonExistingClientId(){
//        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.ofNullable(account));
//        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.empty());
//
//        assertThrows(ClientNotFoundException.class, () -> accountService.editAccountById(uuid, createAccountDTO));
//        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
//    }

    @Test
    @DisplayName("Negative test: Get account by non-existing Id")
    void testGetAccountByNonExistingId(){
        when(accountRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(uuid));
        verify(accountRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Negative test: Create account with null clientId")
    void testCreateAccountWithNullClientId(){
        CreateAccountDTO accountDTOWithNullClientId = DTOCreator.getAccountToCreate();
        accountDTOWithNullClientId.setClientId(null);

        assertThrows(NullPointerException.class, () -> accountService.createAccount(accountDTOWithNullClientId));
        verify(clientRepository, never()).findClientById(any(UUID.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Negative test: Create account with non-existing clientId")
    void testCreateAccountWithNonExistingClientId(){
        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> accountService.createAccount(createAccountDTO));
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Negative test: Delete non-existing account by Id")
    void testDeleteNonExistingAccountById(){
        when(accountRepository.findAccountById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccountById(uuid));
        verify(accountRepository, times(1)).findAccountById(any(UUID.class));
        verify(accountRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Positive test.")
    void testFindAccountsByProductIdAndStatus(){
        when(accountRepository.findAccountByProductIdByStatus(any(UUID.class), any(ProductStatus.class)))
                .thenReturn(accountIdDTOList);
        List<AccountIdDTO> actualAccountIdDTOList = accountService
                .findAccountsByProductIdAndStatus(uuid, ProductStatus.ACTIVE);

        assertEquals(accountIdDTOList.size(), actualAccountIdDTOList.size());
        for (int i = 0; i < accountIdDTOList.size(); i++){
            assertEquals(accountIdDTOList.get(i).getId(), actualAccountIdDTOList.get(i).getId());
        }
        verify(accountRepository, times(1))
                .findAccountByProductIdByStatus(any(UUID.class), any(ProductStatus.class));
    }

    @Test
    @DisplayName("Negative test")
    void testNotFoundAccountIdListDTO(){
        when(accountRepository.findAccountByProductIdByStatus(any(UUID.class), any(ProductStatus.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(NullPointerException.class,
                () -> accountService.findAccountsByProductIdAndStatus(uuid, ProductStatus.ACTIVE));
        verify(accountRepository).findAccountByProductIdByStatus(any(UUID.class), any(ProductStatus.class));
    }
}