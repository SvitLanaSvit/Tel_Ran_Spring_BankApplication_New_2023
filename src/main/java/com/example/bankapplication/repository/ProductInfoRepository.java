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
