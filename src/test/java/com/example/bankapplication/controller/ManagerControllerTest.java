package com.example.bankapplication.controller;

import com.example.bankapplication.dto.*;
import com.example.bankapplication.entity.enums.ManagerStatus;
import com.example.bankapplication.service.ManagerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ManagerController.class)
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateManagerDTO createManagerDTO;
    private ManagerDTO managerDTO;
    private UUID uuid;
    private List<ManagerDTO> managerDTOList;
    private ManagerListDTO managerListDTO;
    private ManagerStatus status;
    private ManagerInfoDTO managerInfoDTO;
    private List<ManagerInfoDTO> managerInfoDTOList;

    @BeforeEach
    void setUp(){
        uuid = UUID.randomUUID();
        createManagerDTO = DTOCreator.getManagerToCreate();
        managerDTO = DTOCreator.getManagerDTO(UUID.randomUUID());
        managerDTOList = new ArrayList<>(List.of(managerDTO));
        managerListDTO = new ManagerListDTO(managerDTOList);
        status = ManagerStatus.ACTIVE;
        managerInfoDTO = DTOCreator.getManagerInfoDTO();
        managerInfoDTOList = new ArrayList<>(List.of(managerInfoDTO));
    }

    @Test
    void testCreate() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createManagerDTO));

        when(managerService.create(any(CreateManagerDTO.class))).thenReturn(managerDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerDTO actualManagerDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerDTO.class);
        compareDTO(managerDTO, actualManagerDTO);
        verify(managerService, times(1)).create(any(CreateManagerDTO.class));
    }

    @Test
    void testGetManagerById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/manager/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(managerService.getManagerById(any(UUID.class))).thenReturn(managerDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerDTO actualManagerDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerDTO.class);
        compareDTO(managerDTO, actualManagerDTO);
        verify(managerService, times(1)).getManagerById(any(UUID.class));
    }

    @Test
    void testGetAllManagers() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/managers")
                .contentType(MediaType.APPLICATION_JSON);

        when(managerService.getManagersStatus()).thenReturn(managerListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerListDTO actualManagerListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerListDTO.class);
        compareListDTO(managerListDTO, actualManagerListDTO);
        verify(managerService, times(1)).getManagersStatus();
    }

    @Test
    void testGetAllManagersByStatus() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/managers/status/{status}", status)
                .contentType(MediaType.APPLICATION_JSON);

        when(managerService.getAllManagersByStatus(any(ManagerStatus.class))).thenReturn(managerListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerListDTO actualManagerListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerListDTO.class);
        compareListDTO(managerListDTO, actualManagerListDTO);
        verify(managerService, times(1)).getAllManagersByStatus(any(ManagerStatus.class));
    }

    @Test
    void testDelete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteManager/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(managerService, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void testEditManager() throws  Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .put("/auth/editManager/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(managerDTO));

        when(managerService.editManagerById(any(UUID.class), any(CreateManagerDTO.class))).thenReturn(managerDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerDTO actualManagerDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerDTO.class);
        compareDTO(managerDTO, actualManagerDTO);
        verify(managerService, times(1)).editManagerById(any(UUID.class), any(CreateManagerDTO.class));
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/managers/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(managerService.getAll()).thenReturn(managerListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ManagerListDTO actualManagerListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ManagerListDTO.class);
        compareListDTO(managerListDTO, actualManagerListDTO);
        verify(managerService, times(1)).getAll();
    }

    @Test
    void testGetAllManagersSortedByProductQuantity() throws Exception {
        RequestBuilder requst = MockMvcRequestBuilders
                .get("/auth/managers/productsQuantity")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findAllManagersSortedByProductQuantity()).thenReturn(managerInfoDTOList);

        var mvcResult = mockMvc.perform(requst).andExpect(status().isOk()).andReturn();
        TypeReference<List<ManagerInfoDTO>> reference = new TypeReference<>() {};
        List<ManagerInfoDTO> actualManagerInfoDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListInfoDTO(managerInfoDTOList, actualManagerInfoDTOList);
        verify(requestService, times(1)).findAllManagersSortedByProductQuantity();
    }

    private void compareDTO (ManagerDTO expectedDTO, ManagerDTO actualDTO){
        assertAll(
                ()->assertEquals(expectedDTO.getId(), actualDTO.getId()),
                ()->assertEquals(expectedDTO.getFirstName(), actualDTO.getFirstName()),
                ()->assertEquals(expectedDTO.getLastName(), actualDTO.getLastName()),
                ()->assertEquals(expectedDTO.getStatus(), actualDTO.getStatus())
        );
    }

    private void compareListDTO(ManagerListDTO expectedListDTO, ManagerListDTO actualListDTO){
        assertEquals(expectedListDTO.getManagerDTOList().size(), actualListDTO.getManagerDTOList().size());
        for(int i = 0; i < expectedListDTO.getManagerDTOList().size(); i++){
            compareDTO(expectedListDTO.getManagerDTOList().get(i), actualListDTO.getManagerDTOList().get(i));
        }
    }

    private void compareListInfoDTO(List<ManagerInfoDTO> expectedListIdDTO, List<ManagerInfoDTO> actualListIdDTO){
        assertEquals(expectedListIdDTO.size(), actualListIdDTO.size());
        for(int i = 0; i < expectedListIdDTO.size(); i++){
            compareInfoDTO(expectedListIdDTO.get(i), actualListIdDTO.get(i));
        }
    }

    private void compareInfoDTO(ManagerInfoDTO expectedDTO, ManagerInfoDTO actualInfoDTO){
        assertAll(
                () -> assertEquals(expectedDTO.getId(), actualInfoDTO.getId()),
                () -> assertEquals(expectedDTO.getFirstName(), actualInfoDTO.getFirstName()),
                () -> assertEquals(expectedDTO.getLastName(), actualInfoDTO.getLastName()),
                () -> assertEquals(expectedDTO.getStatus(), actualInfoDTO.getStatus()),
                () -> assertEquals(expectedDTO.getProductId(), actualInfoDTO.getProductId()),
                () -> assertEquals(expectedDTO.getName(), actualInfoDTO.getName()),
                () -> assertEquals(expectedDTO.getProductLimit(), actualInfoDTO.getProductLimit())
        );
    }
}