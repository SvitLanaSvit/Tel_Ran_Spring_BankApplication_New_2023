package com.example.bankapplication.repository;

import com.example.bankapplication.dto.AgreementIdDTO;
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
 * The `AgreementFindIdsRepository` class is a repository class that provides methods for querying agreement IDs based
 * on a manager ID and client ID. It uses SQL queries to fetch the agreement IDs from the database.
 * <p>
 * `@Repository`: This annotation is used to indicate that this class is a repository component in the Spring framework.
 * <p>
 * `@RequiredArgsConstructor`: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to initialize the `jdbcTemplate` field through constructor injection.
 * <p>
 * `@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)`: This annotation is from the Lombok library
 * and sets the access level of fields to private and makes them final.
 * It eliminates the need to write boilerplate code for field declarations and constructors.
 * <p>
 * `private static final String SQL_REQUEST_MANAGER_ID`: This constant variable holds the SQL query used to fetch agreement IDs
 * associated with a manager ID.
 * <p>
 * `private static final String SQL_REQUEST_CLIENT_ID`: This constant variable holds the SQL query used to fetch agreement IDs
 * associated with a client ID.
 * <p>
 * `NamedParameterJdbcTemplate jdbcTemplate`: This field is used to execute SQL queries with named parameters.
 * It is initialized through constructor injection and made final.
 * <p>
 * `findAgreementsByManagerId(UUID managerId)`: This method queries the database to find agreement IDs associated with a manager.
 * It uses the SQL query `SQL_REQUEST_MANAGER_ID` with a named parameter to retrieve the data.
 * The results are mapped to `AgreementIdDTO` objects and returned as a list.
 * <p>
 * `findAgreementByClientId(UUID clientId)`: This method queries the database to find agreement IDs associated with a client.
 * It uses the SQL query `SQL_REQUEST_CLIENT_ID` with a named parameter to retrieve the data.
 * The results are mapped to `AgreementIdDTO` objects and returned as a list.
 * <p>
 * The `AgreementFindIdsRepository` class provides methods to fetch agreement IDs from the database based on a manager ID and client ID.
 * It uses the `NamedParameterJdbcTemplate` to execute SQL queries.
 */
@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AgreementFindIdsRepository {
    private static final String SQL_REQUEST_MANAGER_ID = "SELECT BIN_TO_UUID(a.id) as id FROM agreements a " +
            "LEFT JOIN products p on p.id = a.product_id " +
            "LEFT JOIN managers m on m.id = p.manager_id " +
            "WHERE m.id = UUID_TO_BIN(:managerId)";

    private static final String SQL_REQUEST_CLIENT_ID = "SELECT BIN_TO_UUID(ag.id) as id FROM agreements ag " +
            "LEFT JOIN accounts ac on ag.account_id = ac.id " +
            "LEFT JOIN clients c on c.id = ac.client_id " +
            "WHERE c.id = UUID_TO_BIN(:clientId);";

    NamedParameterJdbcTemplate jdbcTemplate;

    public List<AgreementIdDTO> findAgreementsByManagerId(UUID managerId) {
        Map<String, Object> map = new HashMap<>();
        map.put("managerId", managerId.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST_MANAGER_ID, map, (rs, rowNum) -> new AgreementIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }

    public List<AgreementIdDTO> findAgreementByClientId(UUID clientId) {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST_CLIENT_ID, map, (rs, rowNum) -> new AgreementIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }
}