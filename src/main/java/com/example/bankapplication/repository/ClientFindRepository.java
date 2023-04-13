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
