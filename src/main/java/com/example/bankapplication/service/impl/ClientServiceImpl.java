package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.entity.enums.ClientStatus;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.exception.ClientNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDTO getClientById(UUID id) {
        log.info("Get an client with id {}", id);
        return clientMapper.toDTO(clientRepository.findClientById(id).orElseThrow(
                () -> new ClientNotFoundException(ErrorMessage.CLIENT_NOT_FOUND)
        ));
    }

    @Override
    public ClientListDTO getClientsStatus() {
        log.info("Get all clients with status active!");
        return new ClientListDTO(clientMapper.clientsToClientsDTO(clientRepository.getAllByStatus(ClientStatus.ACTIVE)));
    }
}
