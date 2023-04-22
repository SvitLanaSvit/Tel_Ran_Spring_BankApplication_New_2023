package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.entity.Client;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
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

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ClientDto")
    void testToDTO() {
        UUID managerId = UUID.randomUUID();
        Client client = EntityCreator.getClient(managerId);
        ClientDTO clientDTO = clientMapper.toDTO(client);
        compareEntityWithDto(client, clientDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ClientDto then return correct entity")
    void testToEntity() {
        ClientDTO clientDTO = DTOCreator.getClientDTO();
        Client client = clientMapper.toEntity(clientDTO);
        compareEntityWithDto(client, clientDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Client then return correct list of ClientDto")
    void testClientsToClientsDTO() {
        UUID managerId = UUID.randomUUID();
        List<Client> clientList = new ArrayList<>();
        clientList.add(EntityCreator.getClient(managerId));

        List<ClientDTO> clientDTOList = clientMapper.clientsToClientsDTO(clientList);
        compareManagerListWithListDto(clientList, clientDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        CreateClientDTO clientDTO = DTOCreator.getClientToCreateWithCreateDate();
        Client client = clientMapper.createToEntity(clientDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String clientDate = date.format(client.getCreatedAt());

        assertNull(clientDTO.getCreatedAt());
        assertNotNull(client.getCreatedAt());
        assertEquals(clientDate, current);
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