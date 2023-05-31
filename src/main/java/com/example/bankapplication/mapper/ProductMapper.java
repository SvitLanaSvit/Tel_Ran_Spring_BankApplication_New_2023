package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.CreateProductDTO;
import com.example.bankapplication.dto.ProductDTO;
import com.example.bankapplication.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `ProductMapper` interface is a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Product` entities and `ProductDTO` data transfer objects.
 * <p>
 * `@Mapper(componentModel = "spring", imports = Timestamp.class)`: This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports = Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 * <p>
 * `@Mapping(source = "product.manager.id", target = "managerId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `manager.id` property
 * of the `Product` entity and the `managerId` property of the `ProductDTO`. It maps the value from `product.manager.id`
 * to `managerId` during the conversion.
 * <p>
 * `ProductDTO toDTO(Product product)`:
 * This method maps a `Product` entity to a `ProductDTO`.
 * <p>
 * `Product toEntity(ProductDTO productDTO)`:
 * This method maps a `ProductDTO` to a `Product` entity.
 * <p>
 * `List<ProductDTO> productsToProductsDTO(List<Product> products)`:
 * This method maps a list of `Product` entities to a list of `ProductDTO` objects.
 * <p>
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Product` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 * <p>
 * `Product createToEntity(CreateProductDTO dto)`:
 * This method maps a `CreateProductDTO` to a `Product` entity, including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */
@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface ProductMapper {
    @Mapping(source = "product.manager.id", target = "managerId")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> productsToProductsDTO(List<Product> products);

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Product createToEntity(CreateProductDTO dto);
}