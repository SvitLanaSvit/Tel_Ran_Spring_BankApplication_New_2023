package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.entity.Product;
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
@DisplayName("Test class for ManagerMapper")
class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapperImpl();

    private UUID uuid;
    private Product product;
    private ProductDTO productDTO;
    private List<Product> productList;
    private List<ProductDTO> productDTOList;
    private CreateProductDTO createProductDTO;

    @BeforeEach
    void setUp(){
        uuid = UUID.randomUUID();
        product = EntityCreator.getProduct(uuid);
        productDTO = DTOCreator.getProductDTO();
        productList = new ArrayList<>(List.of(product));
        productDTOList = new ArrayList<>(List.of(productDTO));
        createProductDTO = DTOCreator.getProductToCreateWithCreateDate();

    }
    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ProductDto")
    void testToDTO() {
        ProductDTO productDTO = productMapper.toDTO(product);
        compareEntityWithDto(product, productDTO);
    }

    @Test
    void testToDTONull() {
        ProductDTO productDTO = productMapper.toDTO(null);
        assertNull(productDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ProductDto then return correct entity")
    void testToEntity() {
        Product product = productMapper.toEntity(productDTO);
        compareEntityWithDto(product, productDTO);
    }

    @Test
    void testToEntityNull() {
        Product product = productMapper.toEntity(null);
        assertNull(product);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Product then return correct list of ProductDto")
    void testProductsToProductsDTO() {
        List<ProductDTO> productDTOList = productMapper.productsToProductsDTO(productList);
        compareManagerListWithListDto(productList, productDTOList);
    }

    @Test
    void testProductsToProductsDTONull() {
        List<ProductDTO> productDTOList = productMapper.productsToProductsDTO(null);
        assertNull(productDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        Product product = productMapper.createToEntity(createProductDTO);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String productDate = date.format(product.getCreatedAt());

        assertNull(createProductDTO.getCreatedAt());
        assertNotNull(product.getCreatedAt());
        assertEquals(current, productDate);
    }

    @Test
    void testCreateToEntityNull() {
        Product product = productMapper.createToEntity(null);;
        assertNull(product);
    }

    private void compareEntityWithDto(Product product, ProductDTO productDTO){
        assertAll(
                () -> assertEquals(product.getId().toString(), productDTO.getId()),
                () -> assertEquals(product.getName(), productDTO.getName()),
                () -> assertEquals(product.getStatus().toString(), productDTO.getStatus()),
                () -> assertEquals(product.getCurrencyCode().toString(), productDTO.getCurrencyCode()),
                () -> assertEquals(Double.toString(product.getInterestRate()), productDTO.getInterestRate()),
                () -> assertEquals(Integer.toString(product.getProductLimit()), productDTO.getProductLimit()),
                () -> assertEquals(product.getCreatedAt(), productDTO.getCreatedAt()),
                () -> assertEquals(product.getUpdatedAt(), productDTO.getUpdatedAt())
        );
    }

    private void compareManagerListWithListDto(List<Product> productList, List<ProductDTO> productDTOList){
        assertEquals(productList.size(), productDTOList.size());
        for(int i = 0; i < productList.size(); i++){
            compareEntityWithDto(productList.get(i), productDTOList.get(i));
        }
    }
}