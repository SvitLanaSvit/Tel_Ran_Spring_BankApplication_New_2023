package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.entity.Manager;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.mapper.ProductMapper;
import com.example.bankapplication.mapper.ProductMapperImpl;
import com.example.bankapplication.repository.ManagerRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.ProductService;
import com.example.bankapplication.service.exception.ManagerNotFoundException;
import com.example.bankapplication.service.exception.NegativeDataException;
import com.example.bankapplication.service.exception.ProductNotFoundException;
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

    private UUID managerId;
    private UUID productId;
    private Product product;
    private List<Product> productList;
    private ProductDTO productDTO;
    private List<ProductDTO> productDTOList;
    private CreateProductDTO createProductDTO;
    private Manager manager;
    private ProductListDTO productListDTO;
    private CreateProductDTO createProductDTONegativeInterestRate;
    private CreateProductDTO createProductDTONegativeProductLimit;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();
        productService = new ProductServiceImpl(productMapper, productRepository, managerRepository);
        managerId = UUID.fromString("08608780-7143-4306-a92f-1937bbcbdebd");
        productId = UUID.fromString("6006ec9c-41a0-4fa1-b8b9-17b4c13347e6");
        product = EntityCreator.getProduct(managerId);
        productList = new ArrayList<>(List.of(product));
        productDTO = DTOCreator.getProductDTO();
        productDTOList = new ArrayList<>(List.of(productDTO));
        createProductDTO = DTOCreator.getProductToCreate();
        manager = EntityCreator.getManager(managerId);
        productListDTO = new ProductListDTO(productDTOList);
        createProductDTONegativeInterestRate = DTOCreator.getProductToCreate();
        createProductDTONegativeInterestRate.setInterestRate("-0.1");
        createProductDTONegativeProductLimit = DTOCreator.getProductToCreate();
        createProductDTONegativeProductLimit.setProductLimit("-1");
    }

    @Test
    void testGetAll() {
        ProductListDTO expectedProductListDTO = new ProductListDTO(productDTOList);

        when(productRepository.findAll()).thenReturn(productList);

        ProductListDTO actualProductListDTO = productService.getAll();
        assertEquals(expectedProductListDTO.getProductDTOList().size(), actualProductListDTO.getProductDTOList().size());
        compareListDto(expectedProductListDTO, actualProductListDTO);
    }

    @Test
    void testGetProductById() {
        ProductDTO expectedProductDTO = productDTO;

        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        ProductDTO actualProductDTO = productService.getProductById(UUID.randomUUID());
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testCreate() {
        Product expectedProduct = EntityCreator.getProductAfterDTO(productId, createProductDTO);
        ProductDTO expectedProductDTO = productDTO;

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.of(manager));

        ProductDTO actualProductDTO = productService.create(createProductDTO);
        assertNotNull(actualProductDTO);
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testEditProductById() {
        ProductDTO expectedProductDTO = productDTO;

        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(managerRepository.findManagerById(any(UUID.class)))
                .thenReturn(Optional.of(manager));

        ProductDTO actualProductDTO = productService.editProductById(UUID.randomUUID(), createProductDTO);
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
        compareEntityWithDto(expectedProductDTO, actualProductDTO);
    }

    @Test
    void testDeleteProductById() {
        when(productRepository.findProductById(any(UUID.class)))
                .thenReturn(Optional.of(product));

        productService.deleteProductById(productId);
        verify(productRepository, times(1)).findProductById(any(UUID.class));
        verify(productRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Negative test. Not found product by Id.")
    public void editProductById_shouldThrowExceptionWhenManagerNotFound() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.editProductById(productId, createProductDTO));
    }

    @Test
    void testGetProductByNonExistingId() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    public void testCreateProductWithNonExistingManagerId() {
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> productService.create(createProductDTO));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    @Test
    public void testEditProductWithNonExistingManagerId() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(managerRepository.findManagerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> productService.editProductById(productId, createProductDTO));
        verify(managerRepository, times(1)).findManagerById(any(UUID.class));
    }

    @Test
    public void testDeleteNonExistingProductById() {
        when(productRepository.findProductById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(productId));
        verify(productRepository, times(1)).findProductById(any(UUID.class));
    }

    @Test
    public void testCreateProductWithNegativeInterestRateNegativeDataException(){
        assertThrows(NegativeDataException.class, () -> productService.create(createProductDTONegativeInterestRate));
    }

    @Test
    public void testCreateProductWithNegativeProductLimitNegativeDataException(){
        assertThrows(NegativeDataException.class, () -> productService.create(createProductDTONegativeProductLimit));
    }

    @Test
    public void testEditeProductWithNegativeInterestRateNegativeDataException(){
        assertThrows(NegativeDataException.class, () -> productService
                .editProductById(managerId, createProductDTONegativeInterestRate));
    }

    @Test
    public void testEditeProductWithNegativeProductLimitNegativeDataException(){
        assertThrows(NegativeDataException.class, () -> productService
                .editProductById(managerId, createProductDTONegativeProductLimit));
    }

    @Test
    void testFindAllChangedProducts() {
        when(productRepository.findAllChangedProducts()).thenReturn(productList);

        ProductListDTO actualProductDTOList = productService
                .findAllChangedProducts();
        assertEquals(productDTOList.size(), actualProductDTOList.getProductDTOList().size());
        for (int i = 0; i < productDTOList.size(); i++) {
            compareEntityWithDto(productDTOList.get(i), actualProductDTOList.getProductDTOList().get(i));
        }
        verify(productRepository, times(1)).findAllChangedProducts();
    }

    private void compareListDto(ProductListDTO expectedProductListDTO, ProductListDTO actualProductListDTO) {
        for (int i = 0; i < expectedProductListDTO.getProductDTOList().size(); i++) {
            compareEntityWithDto(expectedProductListDTO.getProductDTOList().get(i), actualProductListDTO.getProductDTOList().get(i));
        }
    }

    private void compareEntityWithDto(ProductDTO expectedProductDTO, ProductDTO actualProductDTO) {
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