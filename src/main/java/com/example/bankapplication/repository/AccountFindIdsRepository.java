package com.example.bankapplication.repository;

import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.entity.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The AccountFindIdsRepository class is a repository class that provides methods for querying account IDs based
 * on a product ID and status. It uses a SQL query to fetch the account IDs from the database.
 *
 * @Repository: This annotation is used to indicate that this class is a repository component in the Spring framework.
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to initialize the jdbcTemplate field through constructor injection.
 * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE): This annotation is from the Lombok library
 * and sets the access level of fields to private and makes them final.
 * It eliminates the need to write boilerplate code for field declarations and constructors.
 * <p>
 * private static final String SQL_REQUEST: This constant variable holds the SQL query used to fetch account IDs.
 * <p>
 * NamedParameterJdbcTemplate jdbcTemplate: This field is used to execute SQL queries with named parameters.
 * It is initialized through constructor injection and made final.
 * <p>
 * findAccountsByProductIdAndStatus(UUID productId, ProductStatus status):
 * This method queries the database to find account IDs based on the given product ID and status.
 * It uses a SQL query with named parameters to retrieve the data.
 * The results are mapped to AccountIdDTO objects and returned as a list.
 * The AccountFindIdsRepository class provides methods to fetch account IDs from the database based on a product ID and status.
 * It uses the NamedParameterJdbcTemplate to execute SQL queries.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountFindIdsRepository {
    private static final String SQL_REQUEST = "SELECT BIN_TO_UUID(a.id) as id FROM accounts a " +
            "LEFT JOIN clients c ON c.id = a.client_id  " +
            "LEFT JOIN managers m ON m.id = c.manager_id " +
            "LEFT JOIN products p ON p.manager_id = m.id " +
            "WHERE p.id = UUID_TO_BIN(:productId) AND p.status = :status";

    NamedParameterJdbcTemplate jdbcTemplate;

    public List<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId.toString());
        params.put("status", status.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST, params, (rs, rowNum) -> new AccountIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }
}