package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.entity.Product;
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
@DisplayName("Test class for ManagerMapper")
class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapperImpl();
    @Test
    @DisplayName("Positive test. When we have correct entity then return correct ProductDto")
    void testToDTO() {
        UUID managerId = UUID.randomUUID();
        Product product = EntityCreator.getProduct(managerId);
        ProductDTO productDTO = productMapper.toDTO(product);
        compareEntityWithDto(product, productDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct ProductDto then return correct entity")
    void testToEntity() {
        ProductDTO productDTO = DTOCreator.getProductDTO();
        Product product = productMapper.toEntity(productDTO);
        compareEntityWithDto(product, productDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Product then return correct list of ProductDto")
    void testProductsToProductsDTO() {
        UUID managerId = UUID.randomUUID();
        List<Product> productList = new ArrayList<>();
        productList.add(EntityCreator.getProduct(managerId));

        List<ProductDTO> productDTOList = productMapper.productsToProductsDTO(productList);
        compareManagerListWithListDto(productList, productDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        CreateProductDTO dto = DTOCreator.getProductToCreateWithCreateDate();
        Product product = productMapper.createToEntity(dto);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String productDate = date.format(product.getCreatedAt());

        assertNull(dto.getCreatedAt());
        assertNotNull(product.getCreatedAt());
        assertEquals(current, productDate);
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