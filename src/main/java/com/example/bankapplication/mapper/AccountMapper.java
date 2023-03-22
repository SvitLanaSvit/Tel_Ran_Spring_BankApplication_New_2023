package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = UuidMapper.class)
public interface AccountMapper {
    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    List<AccountDTO> accountsToAccountsDTO(List<Account> accounts);
//    default String map(Client value){
//        return value.toString();
//    }
}
