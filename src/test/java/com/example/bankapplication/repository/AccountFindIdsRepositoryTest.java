package com.example.bankapplication.repository;

import com.example.bankapplication.dto.AccountIdDTO;
import com.example.bankapplication.entity.enums.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for AccountFindIdsRepository")
class AccountFindIdsRepositoryTest {
    @Mock(lenient = true)
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private AccountFindIdsRepository findIdsRepository;

    @Test
    @DisplayName("Positive test. Find accounts by product id and status.")
    void testFindAccountsByProductIdAndStatus() {
        UUID productId = UUID.randomUUID();
        ProductStatus status = ProductStatus.ACTIVE;

        List<AccountIdDTO> expected = Collections.singletonList(new AccountIdDTO(UUID.randomUUID()));
        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class))).thenReturn(expected);

        List<AccountIdDTO> listIds = findIdsRepository.findAccountsByProductIdAndStatus(productId, status);
        System.out.println(listIds);
        assertEquals(expected, listIds);
    }
}