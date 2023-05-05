package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientDTO;
import com.example.bankapplication.dto.ClientInfoDTO;
import com.example.bankapplication.dto.ClientListDTO;
import com.example.bankapplication.dto.CreateClientDTO;
import com.example.bankapplication.service.ClientService;
import com.example.bankapplication.service.RequestService;
import com.example.bankapplication.util.DTOCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateClientDTO createClientDTO;
    private ClientDTO clientDTO;
    private UUID uuid;
    private List<ClientDTO> clientDTOList;
    private ClientListDTO clientListDTO;
    private ClientInfoDTO clientInfoDTO;
    private List<ClientInfoDTO> clientInfoDTOList;

    @BeforeEach
    void setUp(){
        createClientDTO = DTOCreator.getClientToCreate();
        clientDTO = DTOCreator.getClientDTO();
        uuid = UUID.randomUUID();
        clientDTOList = new ArrayList<>(List.of(clientDTO));
        clientListDTO = new ClientListDTO(clientDTOList);
        clientInfoDTO = DTOCreator.getClientInfoDTO();
        clientInfoDTOList = new ArrayList<>(List.of(clientInfoDTO));
    }

    @Test
    void testCreateClient() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createClient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientDTO));

        when(clientService.createClient(any(CreateClientDTO.class))).thenReturn(clientDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ClientDTO actualClientDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClientDTO.class);
        compareDTO(clientDTO, actualClientDTO);
        verify(clientService, times(1)).createClient(any(CreateClientDTO.class));
    }

    @Test
    void testGetClientBiId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/client/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(clientService.getClientById(any(UUID.class))).thenReturn(clientDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ClientDTO actialClientDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ClientDTO.class);
        compareDTO(clientDTO, actialClientDTO);
        verify(clientService, times(1)).getClientById(any(UUID.class));
    }

    @Test
    void testGetAllClients() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/clients/active")
                .contentType(MediaType.APPLICATION_JSON);

        when(clientService.getClientsStatus()).thenReturn(clientListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ClientListDTO actualClientListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ClientListDTO.class);
        compareListDTO(clientListDTO, actualClientListDTO);
        verify(clientService, times(1)).getClientsStatus();
    }

    @Test
    void testDeleteClient() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteClient/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(clientService, times(1)).deleteClientById(any(UUID.class));
    }

    @Test
    void testEditClient() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .put("/auth/editClient/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClientDTO));

        when(clientService.editClientById(any(UUID.class), any(CreateClientDTO.class))).thenReturn(clientDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ClientDTO actualClientDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClientDTO.class);
        compareDTO(clientDTO, actualClientDTO);
        verify(clientService, times(1)).editClientById(any(UUID.class), any(CreateClientDTO.class));
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/clients/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(clientService.getAll()).thenReturn(clientListDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ClientListDTO actialClientListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ClientListDTO.class);
        compareListDTO(clientListDTO, actialClientListDTO);
        verify(clientService, times(1)).getAll();
    }

    @Test
    void testGetAllClientsWhereBalanceMoreThan() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/clients/balanceMore?balance=1000")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findClientsWhereBalanceMoreThan(any(Double.class))).thenReturn(clientInfoDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<ClientInfoDTO>> reference = new TypeReference<>() {};
        List<ClientInfoDTO> actualClientInfoDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListInfoDTO(clientInfoDTOList, actualClientInfoDTOList);
        verify(requestService, times(1)).findClientsWhereBalanceMoreThan(any(Double.class));
    }

    private void compareDTO (ClientDTO expectedDTO, ClientDTO actualDTO){
        assertAll(
                ()->assertEquals(expectedDTO.getId(), actualDTO.getId()),
                ()->assertEquals(expectedDTO.getStatus(), actualDTO.getStatus()),
                ()->assertEquals(expectedDTO.getTaxCode(), actualDTO.getTaxCode()),
                ()->assertEquals(expectedDTO.getFirstName(), actualDTO.getFirstName()),
                ()->assertEquals(expectedDTO.getLastName(), actualDTO.getLastName()),
                ()->assertEquals(expectedDTO.getEmail(), actualDTO.getEmail()),
                ()->assertEquals(expectedDTO.getAddress(), actualDTO.getAddress()),
                ()->assertEquals(expectedDTO.getPhone(), actualDTO.getPhone()),
                ()->assertEquals(expectedDTO.getManagerId(), actualDTO.getManagerId())
        );
    }

    private void compareListDTO(ClientListDTO expectedListDTO, ClientListDTO actualListDTO){
        assertEquals(expectedListDTO.getClientDTOList().size(), actualListDTO.getClientDTOList().size());
        for(int i = 0; i < expectedListDTO.getClientDTOList().size(); i++){
            compareDTO(expectedListDTO.getClientDTOList().get(i), actualListDTO.getClientDTOList().get(i));
        }
    }

    private void compareListInfoDTO(List<ClientInfoDTO> expectedListIdDTO, List<ClientInfoDTO> actualListIdDTO){
        assertEquals(expectedListIdDTO.size(), actualListIdDTO.size());
        for(int i = 0; i < expectedListIdDTO.size(); i++){
            compareInfoDTO(expectedListIdDTO.get(i), actualListIdDTO.get(i));
        }
    }

    private void compareInfoDTO(ClientInfoDTO expectedDTO, ClientInfoDTO actualInfoDTO){
        assertAll(
                () -> assertEquals(expectedDTO.getUUID(), actualInfoDTO.getUUID()),
                () -> assertEquals(expectedDTO.getStatus(), actualInfoDTO.getStatus()),
                () -> assertEquals(expectedDTO.getTaxCode(), actualInfoDTO.getTaxCode()),
                () -> assertEquals(expectedDTO.getFirstName(), actualInfoDTO.getFirstName()),
                () -> assertEquals(expectedDTO.getLastName(), actualInfoDTO.getLastName()),
                () -> assertEquals(expectedDTO.getEmail(), actualInfoDTO.getEmail()),
                () -> assertEquals(expectedDTO.getAddress(), actualInfoDTO.getAddress()),
                () -> assertEquals(expectedDTO.getPhone(), actualInfoDTO.getPhone()),
                () -> assertEquals(expectedDTO.getBalance(), actualInfoDTO.getBalance()),
                () -> assertEquals(expectedDTO.getCurrencyCode(), actualInfoDTO.getCurrencyCode())
        );
    }
}