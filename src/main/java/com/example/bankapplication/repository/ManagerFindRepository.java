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

    public List<ManagerInfoDTO> findAllManagersSortedByProductQuantity(){
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
