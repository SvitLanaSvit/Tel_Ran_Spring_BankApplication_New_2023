package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.enums.ClientStatus;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.exception.ClientNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import com.example.bankapplication.service.exception.TaxCodeExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The `ClientServiceImpl` class is an implementation of the `ClientService` interface.
 * It provides methods for performing various operations related to clients.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 *
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
 *
 * @Transactional: This annotation is used in Spring to define transactional boundaries for methods or classes.
 * When applied to a method or class, it indicates that a transaction should be created for the annotated method
 * or all methods within the annotated class.
 * Transactional boundaries ensure that a group of operations are executed as a single atomic unit.
 * If an exception occurs during the execution of the annotated method or any method within the annotated class,
 * the transaction will be rolled back, and any changes made within the transaction will be undone.
 * By using the `@Transactional` annotation, we can manage transactions declaratively without having
 * to write explicit transaction management code. Spring takes care of creating, committing,
 * or rolling back transactions based on the annotated method's execution.
 * It is important to note that the `@Transactional` annotation should be applied to methods that modify data
 * or perform multiple database operations to ensure data integrity and consistency.
 *
 * ClientRepository clientRepository: This field is used to access the client data in the database.
 *
 * ClientMapper clientMapper: This field is used to map client entities to DTOs and vice versa.
 *
 * ManagerRepository managerRepository: This field is used to access manager data in the database.
 *
 * getClientById(UUID id): This method retrieves a client by their unique identifier (`id`).
 * It throws a `ClientNotFoundException` if no client with the specified `id` is found.
 *
 * getClientsStatus(): This method retrieves all clients with the status "ACTIVE".
 *
 * createClient(CreateClientDTO dto): This method creates a new client based on the provided DTO.
 * It throws a `ManagerNotFoundException` if the manager specified in the DTO is not found,
 * and a `TaxCodeExistsException` if a client with the same tax code already exists.
 *
 * deleteClientById(UUID id): This method deletes a client by their unique identifier (`id`).
 * It throws a `ClientNotFoundException` if no client with the specified `id` is found.
 *
 * editClientById(UUID id, CreateClientDTO dto): This method updates a client with the specified `id`
 * using the information provided in the DTO. It throws a `ClientNotFoundException` if no client with the specified `id` is found,
 * and a `ManagerNotFoundException` if the manager specified in the DTO is not found.
 *
 * getAll(): This method retrieves all clients.
 *
 * The `ClientServiceImpl` class implements the `ClientService` interface,
 * which defines the contract for performing operations on clients.
 * By implementing this interface, the class provides the necessary business logic for client-related operations.
 *
 * With the `ClientServiceImpl` class, we can retrieve, create, update, and delete clients,
 * as well as get all clients. It uses the `ClientRepository` and `ManagerRepository` interfaces for data access,
 * and the `ClientMapper` interface for entity-DTO mapping.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public ClientDTO getClientById(UUID id) {
        log.info("Get an client with id {}", id);
        return clientMapper.toDTO(clientRepository.findClientById(id).orElseThrow(
                () -> new ClientNotFoundException(ErrorMessage.CLIENT_NOT_FOUND)
        ));
    }

    @Override
    @Transactional
    public ClientListDTO getClientsStatus() {
        log.info("Get all clients with status active!");
        return new ClientListDTO(clientMapper.clientsToClientsDTO(clientRepository.getAllByStatus(ClientStatus.ACTIVE)));
    }

    @Override
    @Transactional
    public ClientDTO createClient(CreateClientDTO dto) {
        log.info("Creating account");
        var managerUuid = dto.getManagerId();
        log.info("UUID manager: " + managerUuid);
        var manager = managerRepository.findManagerById(managerUuid).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        );
        var client = clientMapper.createToEntity(dto);
        client.setManager(manager);

        var taxCode = dto.getTaxCode();
        clientRepository.findClientByTaxCode(taxCode).ifPresent((elem)->{
            log.info("Client with taxCode {} is exist", dto.getTaxCode());
            throw new TaxCodeExistsException(ErrorMessage.TAX_CODE_EXISTS);
        });

        var result = clientRepository.save(client);
        return clientMapper.toDTO(result);
    }

    @Override
    @Transactional
    public void deleteClientById(UUID id) {
        log.info("Deleting client {}", id);
        var client = clientRepository.findClientById(id);
        if(client.isPresent())
            clientRepository.deleteById(id);
        else throw new ClientNotFoundException(ErrorMessage.CLIENT_NOT_FOUND);
    }

    @Override
    @Transactional
    public ClientDTO editClientById(UUID id, CreateClientDTO dto) {
        var client = clientRepository.findClientById(id).orElseThrow(
                () -> new ClientNotFoundException(ErrorMessage.CLIENT_NOT_FOUND)
        );

        var managerUuid = dto.getManagerId();
        var manager = managerRepository.findManagerById(managerUuid).orElseThrow(
                () -> new ManagerNotFoundException(ErrorMessage.Manager_NOT_FOUND)
        );

        client.setStatus(ClientStatus.valueOf(dto.getStatus()));
        client.setTaxCode(dto.getTaxCode());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        client.setManager(manager);

        var result = clientRepository.save(client);
        return clientMapper.toDTO(result);
    }

    @Override
    @Transactional
    public ClientListDTO getAll() {
        log.info("Get all clients");
        return new ClientListDTO(clientMapper.clientsToClientsDTO(clientRepository.findAll()));
    }
}