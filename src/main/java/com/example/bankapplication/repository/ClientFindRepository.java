package com.example.bankapplication.repository;

import com.example.bankapplication.dto.ClientInfoDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `ClientFindRepository` class is a repository class that provides methods for querying client information
 * based on the account balance. It uses a SQL query to fetch client details from the database.
 *
 * @Repository: This annotation is used to indicate that this class is a repository component in the Spring framework.
 *
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to initialize the `jdbcTemplate` field through constructor injection.
 *
 * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE): This annotation is from the Lombok library
 * and sets the access level of fields to private and makes them final.
 * It eliminates the need to write boilerplate code for field declarations and constructors.
 *
 * private static final String SQL_REQUEST_BALANCE: This constant variable holds the SQL query used to fetch client information
 * based on the account balance.
 *
 * NamedParameterJdbcTemplate jdbcTemplate: This field is used to execute SQL queries with named parameters.
 * It is initialized through constructor injection and made final.
 *
 * findClientWhereBalanceMoreThan(Double balance):
 * This method queries the database to find client information where the account balance is greater than the given balance value.
 * It uses a SQL query with a named parameter to retrieve the data.
 * The results are mapped to `ClientInfoDTO` objects and returned as a list.
 *
 * The `ClientFindRepository` class provides a method to fetch client information based on the account balance from the database.
 * It uses the `NamedParameterJdbcTemplate` to execute SQL queries.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientFindRepository {
    private static final String SQL_REQUEST_BALANCE =
            "SELECT BIN_TO_UUID(c.id) as id, c.status as status, c.tax_code as tax_code, " +
            "c.first_name as first_name, c.last_name as last_name, c.email as email, " +
            "c.address as address, c.phone as phone, a.balance as balance, a.currency_code as currency_code FROM clients c " +
            "LEFT JOIN accounts a on c.id = a.client_id " +
            "WHERE a.balance > :balance";
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<ClientInfoDTO> findClientWhereBalanceMoreThan(Double balance){
        Map<String, Object> params = new HashMap<>();
        params.put("balance", balance);
        return jdbcTemplate.queryForStream(SQL_REQUEST_BALANCE, params, (rs,rowNum) -> new ClientInfoDTO(
            rs.getString("id"),
            rs.getString("status"),
            rs.getString("tax_code"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("address"),
            rs.getString("phone"),
            rs.getString("balance"),
            rs.getString("currency_code")
        )).toList();
    }
}