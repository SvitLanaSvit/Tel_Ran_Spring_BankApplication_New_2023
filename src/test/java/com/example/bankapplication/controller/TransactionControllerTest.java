package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateTransactionDTO;
import com.example.bankapplication.dto.TransactionDTO;
import com.example.bankapplication.dto.TransactionListDTO;
import com.example.bankapplication.service.TransactionService;
import com.example.bankapplication.util.DTOCreator;
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
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;
    TransactionDTO transactionDTO;
    List<TransactionDTO> transactionDTOList;
    TransactionListDTO transactionListDTO;
    UUID uuid;
    CreateTransactionDTO createTransactionDTO;

    @BeforeEach
    void setUp(){
        transactionDTO = DTOCreator.getTransactionDTO();
        transactionDTOList = new ArrayList<>(List.of(transactionDTO));
        transactionListDTO = new TransactionListDTO(transactionDTOList);
        uuid = UUID.randomUUID();
        createTransactionDTO = DTOCreator.getTransactionToCreate();
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/transactions/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(transactionService.getAll()).thenReturn(transactionListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TransactionListDTO actualTransactionListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), TransactionListDTO.class);
        compareListDTO(transactionListDTO, actualTransactionListDTO);
        verify(transactionService, times(1)).getAll();
    }

    @Test
    void testGetTransactionById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/transaction/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(transactionService.getTransactionById(any(UUID.class))).thenReturn(transactionDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TransactionDTO actualTransactionDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), TransactionDTO.class);
        compareDTO(transactionDTO, actualTransactionDTO);
        verify(transactionService, times(1)).getTransactionById(any(UUID.class));
    }

    @Test
    void testCreateTransaction() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTransactionDTO));

        when(transactionService.createTransaction(any(CreateTransactionDTO.class))).thenReturn(transactionDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TransactionDTO actualTransactionDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), TransactionDTO.class);
        compareDTO(transactionDTO, actualTransactionDTO);
        verify(transactionService, times(1)).createTransaction(any(CreateTransactionDTO.class));
    }

    @Test
    void testDeleteTransactionById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteTransaction/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(transactionService, times(1)).deleteTransactionById(any(UUID.class));
    }

    private void compareDTO (TransactionDTO expectedDTO, TransactionDTO actualDTO){
        assertAll(
                ()->assertEquals(expectedDTO.getId(), actualDTO.getId()),
                ()->assertEquals(expectedDTO.getType(), actualDTO.getType()),
                ()->assertEquals(expectedDTO.getAmount(), actualDTO.getAmount()),
                ()->assertEquals(expectedDTO.getDescription(), actualDTO.getDescription()),
                ()->assertEquals(expectedDTO.getDebitAccountId(), actualDTO.getDebitAccountId()),
                ()->assertEquals(expectedDTO.getCreditAccountId(), actualDTO.getCreditAccountId())
        );
    }

    private void compareListDTO(TransactionListDTO expectedListDTO, TransactionListDTO actualListDTO){
        assertEquals(expectedListDTO.getTransactionDTOList().size(), actualListDTO.getTransactionDTOList().size());
        for(int i = 0; i < expectedListDTO.getTransactionDTOList().size(); i++){
            compareDTO(expectedListDTO.getTransactionDTOList().get(i), actualListDTO.getTransactionDTOList().get(i));
        }
    }
}