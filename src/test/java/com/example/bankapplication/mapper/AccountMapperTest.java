package com.example.bankapplication.mapper;

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
}