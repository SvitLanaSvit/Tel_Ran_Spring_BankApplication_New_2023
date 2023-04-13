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

    public List<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status){
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId.toString());
        params.put("status", status.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST, params, (rs, rowNum)-> new AccountIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }
}
