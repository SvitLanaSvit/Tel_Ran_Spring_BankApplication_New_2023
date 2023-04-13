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

    public List<AgreementIdDTO> findAgreementsByManagerId(UUID managerId){
        Map<String, Object> map = new HashMap<>();
        map.put("managerId", managerId.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST_MANAGER_ID, map,(rs, rowNum)-> new AgreementIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }

    public List<AgreementIdDTO> findAgreementByClientId(UUID clientId){
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", clientId.toString());
        return jdbcTemplate.queryForStream(SQL_REQUEST_CLIENT_ID, map, (rs, rowNum) -> new AgreementIdDTO(
                UUID.fromString(rs.getString("id"))
        )).toList();
    }
}
