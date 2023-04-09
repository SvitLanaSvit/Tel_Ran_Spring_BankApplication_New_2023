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
        var clientWithTaxCode = clientRepository.findClientByTaxCode(taxCode);

        if(clientWithTaxCode.isPresent()){
            log.info("Client with taxCode {} is exist", dto.getTaxCode());
            clientMapper.toDTO(clientWithTaxCode.orElseThrow(
                    () -> new TaxCodeExistsException(ErrorMessage.TAX_CODE_EXISTS)
            ));
        }

        var result = clientRepository.save(client);
        return clientMapper.toDTO(result);
    }

    @Override
    @Transactional
    public void deleteClientById(UUID id) {
        log.info("Deleting client {}", id);
        clientRepository.deleteById(id);
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
    public ClientListDTO getAll() {
        log.info("Get all clients");
        return new ClientListDTO(clientMapper.clientsToClientsDTO(clientRepository.findAll()));
    }
}
