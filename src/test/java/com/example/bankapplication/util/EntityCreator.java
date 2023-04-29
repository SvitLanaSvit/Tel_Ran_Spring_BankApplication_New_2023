package com.example.bankapplication.util;

import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.dto.CreateManagerDTO;
import com.example.bankapplication.entity.*;
import com.example.bankapplication.entity.enums.*;
import lombok.Builder;

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
        account.setCurrencyCode(CurrencyCode.USD);
        account.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        account.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        account.setClient(getClient(managerId));
        return account;
    }

    public static Agreement getAgreement(UUID managerId){
        Agreement agreement = new Agreement();
        agreement.setId(UUID.fromString("9c149059-3d2a-4741-9073-a05364ecb6cf"));
        agreement.setInterestRate(0.004);
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setSum(10);
        agreement.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        agreement.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        agreement.setProduct(getProduct(managerId));
        agreement.setAccount(getAccount(managerId));
        return agreement;
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

    public static Product getProduct(UUID managerId){
        Product product = new Product();
        product.setId(UUID.fromString("6006ec9c-41a0-4fa1-b8b9-17b4c13347e6"));
        product.setName("Incredible Bronze Wallet Bank Product");
        product.setStatus(ProductStatus.ACTIVE);
        product.setCurrencyCode(CurrencyCode.USD);
        product.setInterestRate(0.05);
        product.setProductLimit(100);
        product.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        product.setUpdatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        product.setManager(getManager(managerId));
        return product;
    }

    public static Transaction getTransaction(UUID id){
        Transaction transaction = new Transaction();
        var creditAccount = mock(Account.class);
        var debitAccount = mock(Account.class);
        transaction.setId(id);
        transaction.setType(TransactionType.PAYMENT);
        transaction.setAmount(100.0);
        transaction.setDescription("Monthly rent payment");
        transaction.setCreatedAt(Timestamp.valueOf("2023-04-02 00:00:00"));
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        return transaction;
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

    public static Account getAccountAfterDTO(UUID id, CreateAccountDTO dto){
        Account account = new Account();
        account.setId(id);
        account.setName(dto.getName());
        account.setType(AccountType.valueOf(dto.getType()));
        account.setStatus(AccountStatus.valueOf(dto.getStatus()));
        account.setBalance(Double.parseDouble(dto.getBalance()));
        account.setCurrencyCode(CurrencyCode.valueOf(dto.getCurrencyCode()));
        account.setCreatedAt(dto.getCreatedAt());
        account.setUpdatedAt(dto.getUpdatedAt());
        account.setClient(EntityCreator.getClient(UUID.randomUUID()));
        return account;
    }

    public static Agreement getAgreementAfterDTO(UUID id, CreateAgreementDTO agreementDTO){
        Agreement agreement = new Agreement();
        agreement.setId(id);
        agreement.setInterestRate(Double.parseDouble(agreementDTO.getInterestRate()));
        agreement.setStatus(AgreementStatus.valueOf(agreementDTO.getStatus()));
        agreement.setSum(Double.parseDouble(agreementDTO.getSum()));
        agreement.setCreatedAt(agreementDTO.getCreatedAt());
        agreement.setUpdatedAt(agreementDTO.getUpdatedAt());
        agreement.setProduct(EntityCreator.getProduct(UUID.randomUUID()));
        agreement.setAccount(EntityCreator.getAccount(UUID.randomUUID()));
        return agreement;
    }

    public static Client getClientAfterDTO(UUID id, CreateClientDTO createClientDTO){
        Client client = new Client();
        client.setId(id);
        client.setStatus(ClientStatus.valueOf(createClientDTO.getStatus()));
        client.setTaxCode(createClientDTO.getTaxCode());
        client.setFirstName(createClientDTO.getFirstName());
        client.setLastName(createClientDTO.getLastName());
        client.setEmail(createClientDTO.getEmail());
        client.setAddress(createClientDTO.getAddress());
        client.setPhone(createClientDTO.getPhone());
        client.setCreatedAt(createClientDTO.getCreatedAt());
        client.setUpdatedAt(createClientDTO.getUpdatedAt());
        client.setManager(getManager(createClientDTO.getManagerId()));
        return  client;
    }
}
