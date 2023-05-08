package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.entity.enums.ClientStatus;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.mapper.ClientMapperImpl;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.exception.ClientNotFoundException;
import com.example.bankapplication.service.exception.TaxCodeExistsException;
import com.example.bankapplication.util.DTOCreator;
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

    @BeforeEach
    void setUp(){
        clientMapper = new ClientMapperImpl();
        clientService = new ClientServiceImpl(clientRepository, clientMapper, managerRepository);
    }

    @Test
    void testGetClientById() {
        UUID managerId = UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd");
        Client client = EntityCreator.getClient(managerId);
        ClientDTO expectedClientDTO = DTOCreator.getClientDTO();

        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.of(client));
        ClientDTO actualClientDTO = clientService.getClientById(UUID.randomUUID());
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    void testGetClientsStatus() {
        UUID managerId = UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd");
        List<Client> clientList = new ArrayList<>(List.of(EntityCreator.getClient(managerId)));
        List<ClientDTO> clientDTOList = new ArrayList<>(List.of((DTOCreator.getClientDTO())));

        ClientListDTO expectedClientListDTO = new ClientListDTO(clientDTOList);
        when(clientRepository.getAllByStatus(ClientStatus.ACTIVE)).thenReturn(clientList);

        ClientListDTO actualClientListDTO = clientService.getClientsStatus();
        verify(clientRepository, times(1)).getAllByStatus(ClientStatus.ACTIVE);
        assertEquals(expectedClientListDTO.getClientDTOList(), actualClientListDTO.getClientDTOList());
    }

    @Test
    void testCreateClient() {
        UUID clientId = UUID.fromString("06edf03a-d58b-4b26-899f-f4ce69fb6b6f");
        CreateClientDTO createClientDTO = DTOCreator.getClientToCreate();
        Client expectedClient = EntityCreator.getClientAfterDTO(clientId, createClientDTO);
        ClientDTO expectedClientDTO = DTOCreator.getClientDTO();

        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getManager(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));

        ClientDTO actualClientDTO = clientService.createClient(createClientDTO);
        assertNotNull(actualClientDTO);
        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    void testDeleteClientById() {
        UUID clientId = UUID.randomUUID();
        when(clientRepository.findClientById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getClient(UUID.randomUUID())));
        clientService.deleteClientById(clientId);
        verify(clientRepository, times(1)).findClientById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void testEditClientById() {
        Client client = EntityCreator.getClient(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"));
        CreateClientDTO createClientDTO = DTOCreator.getClientToCreate();
        ClientDTO expectedClientDTO = DTOCreator.getClientDTO();

        when(clientRepository.findClientById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getManager(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));

        ClientDTO actualClientDTO = clientService.editClientById(UUID.randomUUID(), createClientDTO);
        verify(clientRepository, times(1)).findClientById(any(UUID.class));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));

        compareEntityWithDto(expectedClientDTO, actualClientDTO);
    }

    @Test
    @DisplayName("Negative test. Not found client by Id.")
    public void editClientById_shouldThrowExceptionWhenManagerNotFound() {
        UUID id = UUID.randomUUID();
        CreateClientDTO dto = DTOCreator.getClientToCreate();

        when(clientRepository.findClientById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.editClientById(id, dto));
    }

    @Test
    @DisplayName("Negative test. Not found taxCode by Id.")
    public void createClientById_shouldThrowTaxCodeExistsException() {
//        UUID id = UUID.randomUUID();
//        CreateClientDTO dto = DTOCreator.getClientToCreate();
//        var taxCode = dto.getTaxCode();
//        when(clientRepository.findClientByTaxCode(taxCode)).thenReturn(null);
//
//        assertThrows(TaxCodeExistsException.class, () -> clientMapper.toDTO(any()));
    }

    @Test
    void testGetAll() {
        List<Client> clientList = new ArrayList<>(List.of(EntityCreator
                .getClient(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));
        List<ClientDTO> clientDTOList = new ArrayList<>(List.of(DTOCreator.getClientDTO()));
        ClientListDTO expectedClientListDTO = new ClientListDTO(clientDTOList);

        when(clientRepository.findAll()).thenReturn(clientList);

        ClientListDTO actualClientListDTO = clientService.getAll();
        assertEquals(expectedClientListDTO.getClientDTOList().size(), actualClientListDTO.getClientDTOList().size());
        assertEquals(expectedClientListDTO.getClientDTOList(), actualClientListDTO.getClientDTOList());
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