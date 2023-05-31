package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.ClientStatus;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for AgreementRepository")
class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private List<Client> clientList;

    @BeforeEach
    void setUp() {
        client = EntityCreator.getClient(UUID.randomUUID());
        clientList = new ArrayList<>(List.of(client));
    }

    @Test
    @DisplayName("Positive test. Find client by id.")
    void testFindClientById() {
        when(clientRepository.findClientById(client.getId())).thenReturn(Optional.of(client));
        Optional<Client> foundClient = clientRepository.findClientById(client.getId());
        assertTrue(foundClient.isPresent());
        assertEquals(client, foundClient.get());
        verify(clientRepository, times(1)).findClientById(client.getId());
    }

    @Test
    @DisplayName("Positive test. Find client by tax code.")
    void testFindClientByTaxCode() {
        when(clientRepository.findClientByTaxCode(client.getTaxCode())).thenReturn(Optional.of(client));
        Optional<Client> foundClient = clientRepository.findClientByTaxCode(client.getTaxCode());
        assertTrue(foundClient.isPresent());
        assertEquals(client, foundClient.get());
        verify(clientRepository, times(1)).findClientByTaxCode(client.getTaxCode());
    }

    @Test
    @DisplayName("Positive test. Find all clients by status.")
    void testGetAllByStatus() {
        when(clientRepository.getAllByStatus(ClientStatus.ACTIVE)).thenReturn(clientList);
        List<Client> foundAccounts = clientRepository.getAllByStatus(ClientStatus.ACTIVE);

        assertEquals(clientList.size(), foundAccounts.size());
        verify(clientRepository, times(1)).getAllByStatus(ClientStatus.ACTIVE);
    }

    @Test
    @DisplayName("Positive test. Find all clients.")
    void testFindAll() {
        when(clientRepository.findAll()).thenReturn(clientList);
        List<Client> foundClients = clientRepository.findAll();

        assertEquals(clientList.size(), foundClients.size());
        verify(clientRepository, times(1)).findAll();
    }
}