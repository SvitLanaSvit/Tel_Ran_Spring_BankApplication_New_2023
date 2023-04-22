package com.example.bankapplication.util;

import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.entity.*;
import com.example.bankapplication.entity.enums.*;

import java.sql.Timestamp;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class EntityCreator {
    public static Manager getManager(UUID id){
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setStatus(ManagerStatus.valueOf("ACTIVE"));
        manager.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        manager.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        return manager;
    }

    public static Account getAccount(UUID managerId){
        Account account = new Account();
        account.setId(UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a"));
        account.setName("MyAccount");
        account.setType(AccountType.STUDENT);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(50);
        account.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        account.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        account.setClient(getClient(managerId));
        return account;
    }

    public static Client getClient(UUID managerId){
        Client client = new Client();
        client.setId(UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f"));
        client.setStatus(ClientStatus.ACTIVE);
        client.setTaxCode("5658380715");
        client.setFirstName("Myles");
        client.setLastName("Mertz");
        client.setEmail("lionel.kassulke@gmail.com");
        client.setAddress("18485 Ronald Tunnel, Edmundoland, NC 91506-5269");
        client.setPhone("869-158-2533 x717");
        client.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        client.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        client.setManager(getManager(managerId));
        return client;
    }

    public static Manager getManagerAfterDTO(UUID id, CreateManagerDTO dto){
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setStatus(ManagerStatus.valueOf(dto.getStatus()));
        manager.setCreatedAt(dto.getCreatedAt());
        manager.setUpdatedAt(dto.getUpdatedAt());
        return manager;
    }

    public static Transaction getTransaction(UUID id){
        Transaction transaction = new Transaction();
        var creditAccount = mock(Account.class);
        var debitAccount = mock(Account.class);
        transaction.setId(id);
        transaction.setType(TransactionType.PAYMENT);
        transaction.setAmount(100);
        transaction.setDescription("Monthly rent payment");
        transaction.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        return transaction;
    }
}
