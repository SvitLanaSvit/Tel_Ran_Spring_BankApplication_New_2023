package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `ProductInfoRepository` class is a repository class that provides methods for retrieving product information
 * from the database. It uses a SQL query to fetch product details for changed products.
 *
 * @Repository: This annotation is used to indicate that this class is a repository component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to initialize the jdbcTemplate field through constructor injection.
 *
 * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE): This annotation is from the Lombok library
 * and sets the access level of fields to private and makes them final.
 * It eliminates the need to write boilerplate code for field declarations and constructors.
 *
 * private static final String SQL_REQUEST_BY_UPDATE: This constant variable holds the SQL query used to fetch product details
 * for changed products.
 *
 * NamedParameterJdbcTemplate jdbcTemplate: This field is used to execute SQL queries with named parameters.
 * It is initialized through constructor injection and made final.
 *
 * findAllChangedProducts(): This method queries the database to find product details for changed products.
 * It uses a SQL query to retrieve the data.
 * The results are mapped to ProductDTO objects and returned as a list.
 *
 * The ProductInfoRepository class provides methods to retrieve product information from the database,
 * specifically for changed products. It uses the NamedParameterJdbcTemplate to execute SQL queries.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductInfoRepository {
    private static final String SQL_REQUEST_BY_UPDATE =
            "SELECT BIN_TO_UUID(p.id) as id, p.name, p.status, p.currency_code, p.interest_rate, " +
                    "p.product_limit, p.created_at, p.updated_at, BIN_TO_UUID(p.manager_id) as manager_id FROM products p " +
            "WHERE updated_at is not null";

    NamedParameterJdbcTemplate jdbcTemplate;

    public List<ProductDTO> findAllChangedProducts(){
        Map<String, Object> params = new HashMap<>();
        return jdbcTemplate.queryForStream(SQL_REQUEST_BY_UPDATE, params, (rs, rowNum) -> new ProductDTO(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("status"),
                rs.getString("currency_code"),
                rs.getString("interest_rate"),
                rs.getString("product_limit"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"),
                rs.getString("manager_id")
        )).toList();
    }
}