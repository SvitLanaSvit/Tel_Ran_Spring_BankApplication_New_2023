package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test class for ClientMapper")
class ClientMapperTest {
    private final ClientMapper clientMapper = new ClientMapperImpl();

    private UUID managerId;
    private Client client;
    private ClientDTO clientDTO;
    private List<Client> clientList;
    private List<ClientDTO> clientDTOList;
    private CreateClientDTO createClientDTO;

    @BeforeEach
    void setUp(){
        managerId = UUID.randomUUID();
        client = EntityCreator.getClient(managerId);
        clientDTO = DTOCreator.getClientDTO();
        clientList = new ArrayList<>(List.of(client));
        clientDTOList = new ArrayList<>(List.of(clientDTO));
        createClientDTO = DTOCreator.getClientToCreateWithCreateDate();
    }

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ClientDto")
    void testToDTO() {
        ClientDTO clientDTO = clientMapper.toDTO(client);
        compareEntityWithDto(client, clientDTO);
    }

    @Test
    void testToDTONull() {
        ClientDTO clientDTO = clientMapper.toDTO(null);
        assertNull(clientDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ClientDto then return correct entity")
    void testToEntity() {
        Client client = clientMapper.toEntity(clientDTO);
        compareEntityWithDto(client, clientDTO);
    }

    @Test
    void testToEntityNull() {
        Client client = clientMapper.toEntity(null);
        assertNull(client);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Client then return correct list of ClientDto")
    void testClientsToClientsDTO() {
        List<ClientDTO> clientDTOList = clientMapper.clientsToClientsDTO(clientList);
        compareManagerListWithListDto(clientList, clientDTOList);
    }

    @Test
    void testClientsToClientsDTONull() {
        List<ClientDTO> clientDTOList = clientMapper.clientsToClientsDTO(null);
        assertNull(clientDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        Client client = clientMapper.createToEntity(createClientDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String clientDate = date.format(client.getCreatedAt());

        assertNull(createClientDTO.getCreatedAt());
        assertNotNull(client.getCreatedAt());
        assertEquals(clientDate, current);
    }

    @Test
    void testCreateToEntityNull() {
        Client client = clientMapper.createToEntity(null);
        assertNull(client);
    }

    @Test
    void testToClientDTO(){
        client.setAccounts(List.of(EntityCreator.getAccount(managerId)));
        ClientInfoDTO clientInfoDTO = clientMapper.toClientDTO(client);
        assertEquals(client.getId().toString(), clientInfoDTO.getId());
        assertEquals(client.getStatus().toString(), clientInfoDTO.getStatus());
        assertEquals(client.getTaxCode(), clientInfoDTO.getTaxCode());
        assertEquals(client.getFirstName(), clientInfoDTO.getFirstName());
        assertEquals(client.getLastName(), clientInfoDTO.getLastName());
        assertEquals(client.getEmail(), clientInfoDTO.getEmail());
        assertEquals(client.getAddress(), clientInfoDTO.getAddress());
        assertEquals(client.getPhone(), clientInfoDTO.getPhone());
        assertEquals(Double.toString(client.getAccounts().get(0).getBalance()), clientInfoDTO.getBalance());
        assertEquals(client.getAccounts().get(0).getCurrencyCode().toString(), clientInfoDTO.getCurrencyCode());
    }

    @Test
    void testToClientDTONull(){
        ClientInfoDTO clientInfoDTO = clientMapper.toClientDTO(null);
        assertNull(clientInfoDTO);
    }

    private void compareEntityWithDto(Client client, ClientDTO clientDTO){
        assertAll(
                () -> assertEquals(client.getId().toString(), clientDTO.getId()),
                () -> assertEquals(client.getStatus().toString(), clientDTO.getStatus()),
                () -> assertEquals(client.getTaxCode(), clientDTO.getTaxCode()),
                () -> assertEquals(client.getFirstName(), clientDTO.getFirstName()),
                () -> assertEquals(client.getLastName(), clientDTO.getLastName()),
                () -> assertEquals(client.getEmail(), clientDTO.getEmail()),
                () -> assertEquals(client.getAddress(), clientDTO.getAddress()),
                () -> assertEquals(client.getPhone(), clientDTO.getPhone()),
                () -> assertEquals(client.getCreatedAt(), clientDTO.getCreatedAt()),
                () -> assertEquals(client.getUpdatedAt(), clientDTO.getUpdatedAt())
        );
    }

    private void compareManagerListWithListDto(List<Client> clientList, List<ClientDTO> clientDTOList){
        assertEquals(clientList.size(), clientDTOList.size());
        for(int i = 0; i < clientList.size(); i++){
            compareEntityWithDto(clientList.get(i), clientDTOList.get(i));
        }
    }
}