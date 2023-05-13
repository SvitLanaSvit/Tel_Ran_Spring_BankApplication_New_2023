package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test class for AccountMapper")
class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapperImpl();

    private UUID uuid;
    private Account account;
    private AccountDTO accountDTO;
    private List<Account> accountList;
    private List<AccountDTO> accountDTOList;
    private CreateAccountDTO createAccountDTO;

    @BeforeEach
    void setUp(){
        uuid = UUID.randomUUID();
        account = EntityCreator.getAccount(uuid);
        accountDTO = DTOCreator.getAccountDTO();
        accountList = new ArrayList<>(List.of(account));
        accountDTOList = new ArrayList<>(List.of(accountDTO));
        createAccountDTO = DTOCreator.getAccountToCreateWithCreateDate();
    }

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct AccountDto")
    void testToDTO() {
        AccountDTO accountDTO = accountMapper.toDTO(account);
        compareEntityWithDto(account, accountDTO);
    }

    @Test
    void testToDTONull() {
        AccountDTO accountDTO = accountMapper.toDTO(null);
        assertNull(accountDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct AccountDto then return correct entity")
    void testToEntity() {
        Account account = accountMapper.toEntity(accountDTO);
        compareEntityWithDto(account, accountDTO);
    }

    @Test
    void testToEntityNull() {
        Account account = accountMapper.toEntity(null);
        assertNull(account);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Account then return correct list of AccountDto")
    void testAccountsToAccountsDTO() {
        List<AccountDTO> accountDTOList = accountMapper.accountsToAccountsDTO(accountList);
        compareManagerListWithListDto(accountList, accountDTOList);
    }

    @Test
    void testAccountsToAccountsDTONull() {
        List<AccountDTO> accountDTOList = accountMapper.accountsToAccountsDTO(null);
        assertNull(accountDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        Account account = accountMapper.createToEntity(createAccountDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String accountDate = date.format(account.getCreatedAt());

        assertNull(createAccountDTO.getCreatedAt());
        assertNotNull(account.getCreatedAt());
        assertEquals(accountDate, current);
    }

    private void compareEntityWithDto(Account account, AccountDTO accountDTO){
        assertAll(
                ()->assertEquals(account.getId().toString(), accountDTO.getId()),
                ()->assertEquals(account.getName(), accountDTO.getName()),
                ()->assertEquals(account.getType().toString(), accountDTO.getType()),
                ()->assertEquals(account.getStatus().toString(), accountDTO.getStatus()),
                ()->assertEquals(Double.toString(account.getBalance()), accountDTO.getBalance())
        );
    }

    private void compareManagerListWithListDto(List<Account> accountList, List<AccountDTO> accountDTOList){
        assertEquals(accountList.size(), accountDTOList.size());
        for (int i = 0; i < accountList.size(); i++){
            compareEntityWithDto(accountList.get(i), accountDTOList.get(i));
        }
    }
}