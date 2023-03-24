package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.entity.Account;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = UuidMapper.class)
public interface AccountMapper {
    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    List<AccountDTO> accountsToAccountsDTO(List<Account> accounts);
}
