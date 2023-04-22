package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.ManagerDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test class for AccountMapper")
class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapperImpl();

    @Test
    @DisplayName("When we have correct entity then return correct AccountDto")
    void testToDTO() {
        UUID uuid = UUID.randomUUID();
        Account account = EntityCreator.getAccount(uuid);
        AccountDTO accountDTO = accountMapper.toDTO(account);
        compareEntityWithDto(account, accountDTO);
    }

    @Test
    void toEntity() {
    }

    @Test
    void accountsToAccountsDTO() {
    }

    @Test
    void createToEntity() {
    }

    private void compareEntityWithDto(Account account, AccountDTO accountDTO){
        assertAll(
                ()->assertEquals(account.getId().toString(), accountDTO.getId()),
                ()->assertEquals(account.getName(), accountDTO.getName())
        );
    }
}