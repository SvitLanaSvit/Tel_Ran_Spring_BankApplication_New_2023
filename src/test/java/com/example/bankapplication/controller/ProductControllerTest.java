package com.example.bankapplication.controller;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.service.ProductService;
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
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private  ProductDTO productDTO;
    private List<ProductDTO> productDTOList;
    private ProductListDTO productListDTO;
    private UUID uuid;
    private CreateProductDTO createProductDTO;

    @BeforeEach
    void setUp(){
        productDTO = DTOCreator.getProductDTO();
        productDTOList = new ArrayList<>(List.of(productDTO));
        productListDTO = new ProductListDTO(productDTOList);
        uuid = UUID.randomUUID();
        createProductDTO = DTOCreator.getProductToCreate();
    }

    @Test
    void testGetAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/products/all")
                .contentType(MediaType.APPLICATION_JSON);

        when(productService.getAll()).thenReturn(productListDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ProductListDTO actualProductListDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ProductListDTO.class);
        compareListDTO(productListDTO, actualProductListDTO);
        verify(productService, times(1)).getAll();
    }

    @Test
    void testGetProductById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/product/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        when(productService.getProductById(any(UUID.class))).thenReturn(productDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ProductDTO actualProductDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
        compareDTO(productDTO, actualProductDTO);
        verify(productService, times(1)).getProductById(any(UUID.class));
    }

    @Test
    void testCreateProduct() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductDTO));

        when(productService.create(any(CreateProductDTO.class))).thenReturn(productDTO);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ProductDTO actualProductDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),ProductDTO.class);
        compareDTO(productDTO, actualProductDTO);
        verify(productService, times(1)).create(any(CreateProductDTO.class));
    }

    @Test
    void testEditProductById() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .put("/auth/editProduct/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductDTO));

        when(productService.editProductById(any(UUID.class), any(CreateProductDTO.class))).thenReturn(productDTO);
        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ProductDTO actualProductDTO = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
        compareDTO(productDTO, actualProductDTO);
        verify(productService, times(1))
                .editProductById(any(UUID.class), any(CreateProductDTO.class));
    }

    @Test
    void testDeleteProductById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/auth/deleteProduct/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(productService, times(1)).deleteProductById(any(UUID.class));
    }

    @Test
    void testGetAllChangedProducts() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/changedProducts")
                .contentType(MediaType.APPLICATION_JSON);

        when(requestService.findAllChangedProducts()).thenReturn(productDTOList);

        var mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        TypeReference<List<ProductDTO>> reference = new TypeReference<>() {};
        List<ProductDTO> actualProductDTOList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), reference);
        compareDTOList(productDTOList, actualProductDTOList);
        verify(requestService, times(1)).findAllChangedProducts();
    }

    private void compareDTO (ProductDTO expectedDTO, ProductDTO actualDTO){
        assertAll(
                ()->assertEquals(expectedDTO.getId(), actualDTO.getId()),
                ()->assertEquals(expectedDTO.getName(), actualDTO.getName()),
                ()->assertEquals(expectedDTO.getStatus(), actualDTO.getStatus()),
                ()->assertEquals(expectedDTO.getCurrencyCode(), actualDTO.getCurrencyCode()),
                ()->assertEquals(expectedDTO.getInterestRate(), actualDTO.getInterestRate()),
                ()->assertEquals(expectedDTO.getProductLimit(), actualDTO.getProductLimit()),
                ()->assertEquals(expectedDTO.getManagerId(), actualDTO.getManagerId())
        );
    }

    private void compareListDTO(ProductListDTO expectedListDTO, ProductListDTO actualListDTO){
        assertEquals(expectedListDTO.getProductDTOList().size(), actualListDTO.getProductDTOList().size());
        for(int i = 0; i < expectedListDTO.getProductDTOList().size(); i++){
            compareDTO(expectedListDTO.getProductDTOList().get(i), actualListDTO.getProductDTOList().get(i));
        }
    }

    private void compareDTOList(List<ProductDTO> expectedDTOList, List<ProductDTO> actualDTOList){
        assertEquals(expectedDTOList.size(), actualDTOList.size());
        for (int i = 0; i < expectedDTOList.size(); i++){
            compareDTO(expectedDTOList.get(i), actualDTOList.get(i));
        }
    }
}