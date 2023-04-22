package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
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

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct AccountDto")
    void testToDTO() {
        UUID uuid = UUID.randomUUID();
        Account account = EntityCreator.getAccount(uuid);
        AccountDTO accountDTO = accountMapper.toDTO(account);
        compareEntityWithDto(account, accountDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct AccountDto then return correct entity")
    void testToEntity() {
        AccountDTO dto = DTOCreator.getAccountDTO();
        Account account = accountMapper.toEntity(dto);
        compareEntityWithDto(account, dto);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Account then return correct list of AccountDto")
    void testAccountsToAccountsDTO() {
        UUID uuid = UUID.randomUUID();
        List<Account> accountList = new ArrayList<>();
        accountList.add(EntityCreator.getAccount(uuid));

        List<AccountDTO> accountDTOList = accountMapper.accountsToAccountsDTO(accountList);
        compareManagerListWithListDto(accountList, accountDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        CreateAccountDTO dto = DTOCreator.getAccountToCreateWithCreateDate();
        Account account = accountMapper.createToEntity(dto);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String accountDate = date.format(account.getCreatedAt());

        assertNull(dto.getCreatedAt());
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