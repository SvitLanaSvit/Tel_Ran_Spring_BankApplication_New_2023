package com.example.bankapplication.service;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ClientDTO getClientById(UUID id);
    ClientListDTO getClientsStatus();
    ClientDTO createClient(CreateClientDTO dto);
    void deleteClientById(UUID id);
    ClientDTO editClientById(UUID id, CreateClientDTO dto);
    ClientListDTO getAll();

    List<ClientInfoDTO> findClientsWhereBalanceMoreThan(Double balance);
}
