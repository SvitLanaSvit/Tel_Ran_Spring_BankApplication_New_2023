package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.enums.ClientStatus;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.mapper.ClientMapperImpl;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.exception.ClientNotFoundException;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import com.example.bankapplication.service.exception.TaxCodeExistsException;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for ClientServiceImpl")
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ManagerRepository managerRepository;

    private ClientMapper clientMapper;

    private ClientService clientService;

    private UUID uuid;
    private Client client;
    private ClientDTO clientDTO;
    private List<ClientDTO> clientDTOList;
    private UUID managerId;
    private List<Client> clientList;
    private CreateClientDTO createClientDTO;
    private UUID clientId;
    private Manager manager;
    private ClientInfoDTO clientInfoDTO;

    @BeforeEach
    void setUp(){
        clientMapper = new ClientMapperImpl();
        clientService = new ClientServiceImpl(clientRepository, clientMapper, managerRepository);
        uuid = UUID.randomUUID();
        managerId = UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd");
        client = EntityCreator.getClient(managerId);
        clientDTO = DTOCreator.getClientDTO();
        clientList = new ArrayList<>(List.of(client));
        clientDTOList = new ArrayList<>(List.of(clientDTO));
        createClientDTO = DTOCreator.getClientToCreate();
        clientId = UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f");
        manager = EntityCreator.getManager(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"));
        clientInfoDTO = DTOCreator.getClientInfoDTO();
    }

    @Test
    void testGetClientById() {
        ClientDTO expectedClientDTO = clientDTO;

        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.of(client));
        ClientDTO actualClientDTO = clientService.getClientById(uuid);
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    void testGetClientsStatus() {
        ClientListDTO expectedClientListDTO = new ClientListDTO(clientDTOList);
        when(clientRepository.getAllByStatus(ClientStatus.ACTIVE)).thenReturn(clientList);

        ClientListDTO actualClientListDTO = clientService.getClientsStatus();
        verify(clientRepository, times(1)).getAllByStatus(ClientStatus.ACTIVE);
        assertEquals(expectedClientListDTO.getClientDTOList(), actualClientListDTO.getClientDTOList());
    }

    @Test
    void testCreateClient() {
        Client expectedClient = EntityCreator.getClientAfterDTO(clientId, createClientDTO);
        ClientDTO expectedClientDTO = clientDTO;

        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(manager));

        ClientDTO actualClientDTO = clientService.createClient(createClientDTO);
        assertNotNull(actualClientDTO);
        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    void testDeleteClientById() {
        when(clientRepository.findClientById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getClient(UUID.randomUUID())));
        clientService.deleteClientById(uuid);
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        verify(clientRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void testEditClientById() {
        ClientDTO expectedClientDTO = clientDTO;

        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.of(manager));

        ClientDTO actualClientDTO = clientService.editClientById(uuid, createClientDTO);
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));

        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    @DisplayName("Negative test. Not found client by Id.")
    void editClientById_shouldThrowExceptionWhenManagerNotFound() {
        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.editClientById(uuid, createClientDTO));
    }

    @Test
    @DisplayName("Negative test. Not found taxCode.")
    void createClientById_shouldThrowTaxCodeExistsException() {
        String existingTaxCode = "1234567890";
        createClientDTO.setTaxCode(existingTaxCode);

        when(managerRepository.findManagerById(any())).thenReturn(Optional.of(manager));
        when(clientRepository.findClientByTaxCode(existingTaxCode))
                .thenReturn(Optional.of(client));

        assertThrows(TaxCodeExistsException.class, () -> clientService.createClient(createClientDTO));
    }

    @Test
    void testGetAll() {
        ClientListDTO expectedClientListDTO = new ClientListDTO(clientDTOList);

        when(clientRepository.findAll()).thenReturn(clientList);

        ClientListDTO actualClientListDTO = clientService.getAll();
        assertEquals(expectedClientListDTO.getClientDTOList().size(), actualClientListDTO.getClientDTOList().size());
        assertEquals(expectedClientListDTO.getClientDTOList(), actualClientListDTO.getClientDTOList());
    }

    @Test
    void testGetClientByNonExistingId(){
        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(uuid));
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
    }

    @Test
    void testCreateClientWithNoExistingManagerId(){
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ManagerNotFoundException.class, () -> clientService.createClient(createClientDTO));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    @Test
    void testDeleteNonExistingClientById(){
        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClientById(clientId));
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
    }

    @Test
    void testEditClientWithNonExistingManagerId(){
        when(clientRepository.findClientById(any())).thenReturn(Optional.ofNullable(client));
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ManagerNotFoundException.class, () -> clientService.editClientById(clientId, createClientDTO));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    private void compareEntityWithDto(ClientDTO expectedClientDTO, ClientDTO actualClientDTO){
        assertAll(
                () -> assertEquals(expectedClientDTO.getId(), actualClientDTO.getId()),
                () -> assertEquals(expectedClientDTO.getStatus(), actualClientDTO.getStatus()),
                () -> assertEquals(expectedClientDTO.getTaxCode(), actualClientDTO.getTaxCode()),
                () -> assertEquals(expectedClientDTO.getFirstName(), actualClientDTO.getFirstName()),
                () -> assertEquals(expectedClientDTO.getLastName(), actualClientDTO.getLastName()),
                () -> assertEquals(expectedClientDTO.getEmail(), actualClientDTO.getEmail()),
                () -> assertEquals(expectedClientDTO.getAddress(), actualClientDTO.getAddress()),
                () -> assertEquals(expectedClientDTO.getPhone(), actualClientDTO.getPhone()),
                () -> assertEquals(expectedClientDTO.getManagerId(), actualClientDTO.getManagerId())
        );
    }
}