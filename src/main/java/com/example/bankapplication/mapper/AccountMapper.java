package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface AccountMapper {
    //Annotation '@Mapping' write if we need only clientId, otherwise we remove it and in AccountDTO instead of 'String clientId'
    // write ClientDTO client
    @Mapping(source = "account.client.id", target = "clientId")
    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    List<AccountDTO> accountsToAccountsDTO(List<Account> accounts);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Account createToEntity(CreateAccountDTO dto);
}
