package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.dto.ProductListDTO;
import com.example.bankapplication.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", uses = UuidMapper.class, imports = Timestamp.class)
public interface ProductMapper {
    @Mapping(source = "product.manager.id", target = "managerId")
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
    List<ProductDTO> productsToProductsDTO(List<Product> products);
}
