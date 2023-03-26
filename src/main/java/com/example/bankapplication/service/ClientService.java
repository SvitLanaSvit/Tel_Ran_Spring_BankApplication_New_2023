package com.example.bankapplication.service;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientListDTO;
import java.util.UUID;

public interface ClientService {
    ClientDTO getClientById(UUID id);
    ClientListDTO getClientsStatus();
}
