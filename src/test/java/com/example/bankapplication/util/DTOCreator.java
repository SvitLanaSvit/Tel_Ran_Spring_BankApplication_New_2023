package com.example.bankapplication.util;

import com.example.bankapplication.dto.*;

import java.sql.Timestamp;
import java.util.UUID;

public class DTOCreator {
    public static ManagerDTO getManagerDTO(UUID id) {
        return new ManagerDTO(
                id.toString(),
                "John",
                "Doe",
                "ACTIVE",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00")
        );
    }

    public static AccountDTO getAccountDTO() {
        return new AccountDTO(
                "2c1a2a48-63f8-4931-bcf5-353f16fdbd7a",
                "MyAccount",
                "STUDENT",
                "ACTIVE",
                "50.0",
                "USD",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                "06edf03a-d58b-4b26-899f-f4ce69fb6b6f"
        );
    }

    public static AgreementDTO getAgreementDTO() {
        return new AgreementDTO(
                "9c149059-3d2a-4741-9073-a05364ecb6cf",
                "0.004",
                "ACTIVE",
                "10.0",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                "6006ec9c-41a0-4fa1-b8b9-17b4c13347e6",
                "2c1a2a48-63f8-4931-bcf5-353f16fdbd7a"
        );
    }

    public static ClientDTO getClientDTO() {
        return new ClientDTO(
                "06edf03a-d58b-4b26-899f-f4ce69fb6b6f",
                "ACTIVE",
                "5658380715",
                "Myles",
                "Mertz",
                "lionel.kassulke@gmail.com",
                "18485 Ronald Tunnel, Edmundoland, NC 91506-5269",
                "869-158-2533 x717",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                "08608780-7143-4306-a92f-1937bbcbdebd"
        );
    }

    public static ProductDTO getProductDTO() {
        return new ProductDTO(
                "6006ec9c-41a0-4fa1-b8b9-17b4c13347e6",
                "Incredible Bronze Wallet Bank Product",
                "ACTIVE",
                "USD",
                "0.05",
                "100",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                "08608780-7143-4306-a92f-1937bbcbdebd"
        );
    }

    public static TransactionDTO getTransactionDTO() {
        return new TransactionDTO(
                "72779690-8d70-43cf-97a8-d3e7b9076337",
                "PAYMENT",
                "100.0",
                "Monthly rent payment",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                "2c1a2a48-63f8-4931-bcf5-353f16fdbd7a",
                "2c1a2a48-63f8-4931-bcf5-353f16fdbd7a"
        );
    }

    public static AccountIdDTO getAccountIdDTO() {
        return new AccountIdDTO(
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a")
        );
    }

    public static AgreementIdDTO getAgreementIdDTO() {
        return new AgreementIdDTO(
                UUID.fromString("9c149059-3d2a-4741-9073-a05364ecb6cf")
        );
    }

    public static ClientInfoDTO getClientInfoDTO() {
        return new ClientInfoDTO(
                "06edf03a-d58b-4b26-899f-f4ce69fb6b6f",
                "ACTIVE",
                "5658380715",
                "Myles",
                "Mertz",
                "lionel.kassulke@gmail.com",
                "18485 Ronald Tunnel, Edmundoland, NC 91506-5269",
                "869-158-2533 x717",
                "5000,0",
                "USD"
        );
    }

    public static ManagerInfoDTO getManagerInfoDTO() {
        return new ManagerInfoDTO(
                UUID.randomUUID().toString(),
                "John",
                "Doe",
                "ACTIVE",
                "6006ec9c-41a0-4fa1-b8b9-17b4c13347e6",
                "Incredible Bronze Wallet Bank Product",
                "100"
        );
    }

    public static CreateManagerDTO getManagerToCreate() {
        return new CreateManagerDTO(
                "John",
                "Doe",
                "ACTIVE",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00")
        );
    }

    public static CreateAccountDTO getAccountToCreate() {
        return new CreateAccountDTO(
                "MyAccount",
                "STUDENT",
                "ACTIVE",
                "50.0",
                "USD",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f")
        );
    }

    public static CreateAgreementDTO getAgreementToCreate() {
        return new CreateAgreementDTO(
                "0.004",
                "ACTIVE",
                "10.0",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                UUID.fromString("6006ec9c-41a0-4fa1-b8b9-17b4c13347e6"),
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a")
        );
    }

    public static CreateClientDTO getClientToCreate() {
        return new CreateClientDTO(
                "ACTIVE",
                "5658380715",
                "Myles",
                "Mertz",
                "lionel.kassulke@gmail.com",
                "18485 Ronald Tunnel, Edmundoland, NC 91506-5269",
                "869-158-2533 x717",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd")
        );
    }

    public static CreateProductDTO getProductToCreate() {
        return new CreateProductDTO(
                "Incredible Bronze Wallet Bank Product",
                "ACTIVE",
                "USD",
                "0.05",
                "100",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                Timestamp.valueOf("2023-04-02 00:00:00"),
                UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd")
        );
    }

    public static CreateTransactionDTO getTransactionToCreate() {
        return new CreateTransactionDTO(
                "PAYMENT",
                "100.0",
                "Monthly rent payment",
                Timestamp.valueOf("2023-04-02 00:00:00"),
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a"),
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a")
        );
    }

    public static CreateManagerDTO getManagerToCreateWithCreateDate() {
        return new CreateManagerDTO(
                "John",
                "Doe",
                "ACTIVE",
                null,
                null
        );
    }

    public static CreateAccountDTO getAccountToCreateWithCreateDate() {
        return new CreateAccountDTO(
                "MyAccount",
                "STUDENT",
                "ACTIVE",
                "50.0",
                "USD",
                null,
                null,
                UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f")
        );
    }

    public static CreateAgreementDTO getAgreementToCreateWithCreateDate() {
        return new CreateAgreementDTO(
                "0.004",
                "ACTIVE",
                "10.0",
                null,
                null,
                UUID.fromString("6006ec9c-41a0-4fa1-b8b9-17b4c13347e6"),
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a")
        );
    }

    public static CreateClientDTO getClientToCreateWithCreateDate() {
        return new CreateClientDTO(
                "ACTIVE",
                "5658380715",
                "Myles",
                "Mertz",
                "lionel.kassulke@gmail.com",
                "18485 Ronald Tunnel, Edmundoland, NC 91506-5269",
                "869-158-2533 x717",
                null,
                null,
                UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd")
        );
    }

    public static CreateProductDTO getProductToCreateWithCreateDate() {
        return new CreateProductDTO(
                "Incredible Bronze Wallet Bank Product",
                "ACTIVE",
                "USD",
                "0.05",
                "100",
                null,
                null,
                UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd")
        );
    }

    public static CreateTransactionDTO getTransactionToCreateWithCreateDate() {
        return new CreateTransactionDTO(
                "PAYMENT",
                "100.0",
                "Monthly rent payment",
                null,
                UUID.fromString("2c1a2a48-63f8-4931-bcf5-353f16fdbd7a"),
                UUID.fromString("3c9f22ea-1e34-40c8-858b-509f1609e60d")
        );
    }


}
