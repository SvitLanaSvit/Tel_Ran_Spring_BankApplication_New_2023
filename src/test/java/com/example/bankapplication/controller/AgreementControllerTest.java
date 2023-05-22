package com.example.bankapplication.controller;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementIdDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.service.AgreementService;
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
@WebMvcTest(controllers = AgreementController.class)
class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgreementService agreementService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private AgreementListDTO agreementListDTO;
    private List<AgreementDTO> agreementDTOList;
    private AgreementDTO agreementDTO;
    private UUID uuid;
    private CreateAgreementDTO createAgreementDTO;
    private AgreementIdDTO agreementIdDTO;
    private List<AgreementIdDTO> agreementIdDTOList;

    @BeforeEach
    void setUp(){
        agreementDTO = DTOCreator.getAgreementDTO();
        agreementDTOList = new ArrayList<>(List.of(agreementDTO));
        agreementListDTO = new AgreementListDTO(agreementDTOList);
        uuid = UUID.randomUUID();
        createAgreementDTO = DTOCreator.getAgreementToCreate();
        agreementIdDTO = DTOCreator.getAgreementIdDTO();
        agreementIdDTOList = new ArrayList<>(List.of(agreementIdDTO));
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/agreements/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(agreementService.getAll()).thenReturn(agreementListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AgreementListDTO actualAgreementListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AgreementListDTO.class);
        compareListDTO(agreementListDTO, actualAgreementListDTO);
        verify(agreementService, times(1)).getAll();
    }

    @Test
    void testGetAgreementById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/agreement/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(agreementService.getAgreementById(any(UUID.class))).thenReturn(agreementDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AgreementDTO actualAgreementDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AgreementDTO.class);
        compareDTO(agreementDTO, actualAgreementDTO);
        verify(agreementService, times(1)).getAgreementById(any(UUID.class));
    }

    @Test
    void testCreateAgreement() throws  Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createAgreement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAgreementDTO));

        when(agreementService.createAgreement(any(CreateAgreementDTO.class))).thenReturn(agreementDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AgreementDTO actualAgreementDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AgreementDTO.class);
        compareDTO(agreementDTO, actualAgreementDTO);
        verify(agreementService, times(1)).createAgreement(any(CreateAgreementDTO.class));
    }

    @Test
    void testEditAgreement() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .put("/auth/editAgreement/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAgreementDTO));

        when(agreementService.editAgreementById(any(UUID.class), any(CreateAgreementDTO.class))).thenReturn(agreementDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        AgreementDTO actualeAgreementDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AgreementDTO.class);
        compareDTO(agreementDTO, actualeAgreementDTO);
        verify(agreementService, times(1))
                .editAgreementById(any(UUID.class), any(CreateAgreementDTO.class));
    }

    @Test
    void testDeleteAgreementById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteAgreement/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(agreementService, times(1)).deleteAgreementById(any(UUID.class));
    }

    @Test
    void testGetAgreementsByManagerId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAgreements/ManagerId?managerId=0eb587d1-5ccd-4a9f-9556-aa0be1fab212")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findAgreementsByManagerId(any(UUID.class))).thenReturn(agreementIdDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<AgreementIdDTO>> reference = new TypeReference<>() {};
        List<AgreementIdDTO> actualAgreementIdDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListIdDTO(agreementIdDTOList, actualAgreementIdDTOList);
        verify(requestService, times(1)).findAgreementsByManagerId(any(UUID.class));
    }

    @Test
    void testGetAgreementsByManagerIdJPA() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAgreementsJPA/ManagerId?managerId=0eb587d1-5ccd-4a9f-9556-aa0be1fab212")
                .contentType(MediaType.APPLICATION_JSON);

        when(agreementService.findAgreementsByManagerId(any(UUID.class))).thenReturn(agreementIdDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<AgreementIdDTO>> reference = new TypeReference<>() {};
        List<AgreementIdDTO> actualAgreementIdDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListIdDTO(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementService, times(1)).findAgreementsByManagerId(any(UUID.class));
    }

    @Test
    void testGetAgreementsByClientId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAgreements/ClientId?clientId=34fe63b8-0958-49f3-b0c4-f9f50744a77f")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findAgreementByClientId(any(UUID.class))).thenReturn(agreementIdDTOList);
        TypeReference<List<AgreementIdDTO>> reference = new TypeReference<>() {};
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        List<AgreementIdDTO> actualAgreementIdDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListIdDTO(agreementIdDTOList, actualAgreementIdDTOList);
        verify(requestService, times(1)).findAgreementByClientId(any(UUID.class));
    }

    @Test
    void testGetAgreementsByClientIdJPA() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/findAgreementsJPA/ClientId?clientId=34fe63b8-0958-49f3-b0c4-f9f50744a77f")
                .contentType(MediaType.APPLICATION_JSON);

        when(agreementService.findAgreementByClientId(any(UUID.class))).thenReturn(agreementIdDTOList);
        TypeReference<List<AgreementIdDTO>> reference = new TypeReference<>() {};
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        List<AgreementIdDTO> actualAgreementIdDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareListIdDTO(agreementIdDTOList, actualAgreementIdDTOList);
        verify(agreementService, times(1)).findAgreementByClientId(any(UUID.class));
    }

    private void compareDTO (AgreementDTO expectedDTO, AgreementDTO actualDTO){
        assertAll(
                ()->assertEquals(expectedDTO.getId(), actualDTO.getId()),
                ()->assertEquals(expectedDTO.getInterestRate(), actualDTO.getInterestRate()),
                ()->assertEquals(expectedDTO.getStatus(), actualDTO.getStatus()),
                ()->assertEquals(expectedDTO.getSum(), actualDTO.getSum()),
                ()->assertEquals(expectedDTO.getProductId(), actualDTO.getProductId()),
                ()->assertEquals(expectedDTO.getAccountId(), actualDTO.getAccountId())
        );
    }

    private void compareListDTO(AgreementListDTO expectedListDTO, AgreementListDTO actualListDTO){
        assertEquals(expectedListDTO.getAgreementDTOList().size(), actualListDTO.getAgreementDTOList().size());
        for(int i = 0; i < expectedListDTO.getAgreementDTOList().size(); i++){
            compareDTO(expectedListDTO.getAgreementDTOList().get(i), actualListDTO.getAgreementDTOList().get(i));
        }
    }

    private void compareListIdDTO(List<AgreementIdDTO> expectedListIdDTO, List<AgreementIdDTO> actualListIdDTO){
        assertEquals(expectedListIdDTO.size(), actualListIdDTO.size());
        for(int i = 0; i < expectedListIdDTO.size(); i++){
            assertEquals(expectedListIdDTO.get(i).getId(), actualListIdDTO.get(i).getId());
        }
    }
}