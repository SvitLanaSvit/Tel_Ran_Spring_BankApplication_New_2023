package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ManagerInfoDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `ManagerFindRepository` class is a repository class that provides a method for retrieving manager information
 * sorted by product quantity. It uses a SQL query to fetch the data from the database.
 *
 * @Repository: This annotation is used to indicate that this class is a repository component in the Spring framework.
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to initialize the jdbcTemplate field through constructor injection.
 * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE): This annotation is from the Lombok library
 * and sets the access level of fields to private and makes them final.
 * It eliminates the need to write boilerplate code for field declarations and constructors.
 * <p>
 * private static final String SQL_REQUEST: This constant variable holds the SQL query used to fetch manager information.
 * The query retrieves the manager ID, first name, last name, status, product ID, product name, and product limit
 * from the managers and products tables, respectively, and orders the results by product limit.
 * <p>
 * NamedParameterJdbcTemplate jdbcTemplate: This field is used to execute SQL queries with named parameters.
 * It is initialized through constructor injection and made final.
 * <p>
 * findAllManagersSortedByProductQuantity():
 * This method queries the database to retrieve manager information sorted by product quantity.
 * It uses a SQL query to fetch the data and maps it to ManagerInfoDTO objects.
 * The results are returned as a list.
 * <p>
 * The ManagerFindRepository class provides a method to fetch manager information from the database
 * sorted by product quantity. It uses the NamedParameterJdbcTemplate to execute the SQL query.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ManagerFindRepository {
    private static final String SQL_REQUEST =
            "SELECT BIN_TO_UUID(m.id) as manager_id, m.first_name, m.last_name, m.status, " +
                    "BIN_TO_UUID(p.id) as product_id, p.name, p.product_limit FROM managers m " +
                    "LEFT JOIN products p on p.manager_id = m.id " +
                    "ORDER BY product_limit";

    NamedParameterJdbcTemplate jdbcTemplate;

    public List<ManagerInfoDTO> findAllManagersSortedByProductQuantity() {
        Map<String, Object> params = new HashMap<>();

        return jdbcTemplate.queryForStream(SQL_REQUEST, params, (rs, rowNum) -> new ManagerInfoDTO(
                rs.getString("manager_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("status"),
                rs.getString("product_id"),
                rs.getString("name"),
                rs.getString("product_limit")
        )).toList();
    }
}