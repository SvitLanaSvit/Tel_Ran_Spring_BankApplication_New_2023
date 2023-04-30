package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateAccountDTO;
import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.mapper.ProductMapper;
import com.example.bankapplication.mapper.ProductMapperImpl;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.ProductService;
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
@DisplayName("Test class for ProductServiceImpl")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManagerRepository managerRepository;

    private ProductMapper productMapper;

    private ProductService productService;

    @BeforeEach
    void setUp(){
        productMapper = new ProductMapperImpl();
        productService = new ProductServiceImpl(productMapper, productRepository, managerRepository);
    }

    @Test
    void testGetAll() {
        List<Product> productList = new ArrayList<>(List.of(EntityCreator
                .getProduct(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));
        List<ProductDTO> productDTOList = new ArrayList<>(List.of(DTOCreator.getProductDTO()));
        ProductListDTO expectedProductListDTO = new ProductListDTO(productDTOList);

        when(productRepository.findAll()).thenReturn(productList);

        ProductListDTO actualProductListDTO = productService.getAll();
        assertEquals(expectedProductListDTO.getProductDTOList().size(), actualProductListDTO.getProductDTOList().size());
        compareListDto(expectedProductListDTO, actualProductListDTO);
    }

    @Test
    void testGetProductById() {
        Product product = EntityCreator.getProduct(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"));
        ProductDTO expectedProductDTO = DTOCreator.getProductDTO();

        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        ProductDTO actualProductDTO = productService.getProductById(UUID.randomUUID());
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testCreate() {
        UUID productId = UUID.fromString("6006ec9c-41a0-4fa1-b8b9-17b4c13347e6");
        CreateProductDTO createProductDTO = DTOCreator.getProductToCreate();
        Product expectedProduct = EntityCreator.getProductAfterDTO(productId, createProductDTO);
        ProductDTO expectedProductDTO = DTOCreator.getProductDTO();

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getManager(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));

        ProductDTO actualProductDTO = productService.create(createProductDTO);
        assertNotNull(actualProductDTO);
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testEditProductById() {
        Product product = EntityCreator.getProduct(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"));
        CreateProductDTO createProductDTO = DTOCreator.getProductToCreate();
        ProductDTO expectedProductDTO = DTOCreator.getProductDTO();

        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getManager(UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd"))));

        ProductDTO actualProductDTO = productService.editProductById(UUID.randomUUID(), createProductDTO);
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testDeleteProductById() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(EntityCreator.getProduct(UUID.randomUUID())));

        productService.deleteProductById(productId);
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        verify(productRepository, times(1)).deleteById(any(UUID.class));
    }

    private void compareListDto(ProductListDTO expectedProductListDTO, ProductListDTO actualProductListDTO){
        for(int i = 0; i < expectedProductListDTO.getProductDTOList().size(); i++){
            compareEntityWithDto(expectedProductListDTO.getProductDTOList().get(i), actualProductListDTO.getProductDTOList().get(i));
        }
    }

    private void compareEntityWithDto(ProductDTO expectedProductDTO, ProductDTO actualProductDTO){
        assertAll(
                () -> assertEquals(expectedProductDTO.getId(), actualProductDTO.getId()),
                () -> assertEquals(expectedProductDTO.getName(), actualProductDTO.getName()),
                () -> assertEquals(expectedProductDTO.getStatus(), actualProductDTO.getStatus()),
                () -> assertEquals(expectedProductDTO.getCurrencyCode(), actualProductDTO.getCurrencyCode()),
                () -> assertEquals(expectedProductDTO.getInterestRate(), actualProductDTO.getInterestRate()),
                () -> assertEquals(expectedProductDTO.getProductLimit(), actualProductDTO.getProductLimit()),
                () -> assertEquals(expectedProductDTO.getManagerId(), actualProductDTO.getManagerId())
        );
    }
}