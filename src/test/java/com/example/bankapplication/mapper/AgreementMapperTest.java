package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AccountDTO;
import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Agreement;
import com.example.bankapplication.util.DTOCreator;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test class for AgreementMapper")
class AgreementMapperTest {

    private final AgreementMapper agreementMapper = new AgreementMapperImpl();

    @Test
    @DisplayName("Positive test. When we have correct entity then return correct AgreementDto")
    void testToDTO() {
        UUID managerId = UUID.randomUUID();
        Agreement agreement = EntityCreator.getAgreement(managerId);
        AgreementDTO agreementDTO = agreementMapper.toDTO(agreement);
        compareEntityWithDto(agreement, agreementDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct AgreementDto then return correct entity")
    void testToEntity() {
        AgreementDTO agreementDTO = DTOCreator.getAgreementDTO();
        Agreement agreement = agreementMapper.toEntity(agreementDTO);
        compareEntityWithDto(agreement, agreementDTO);
    }

    @Test
    @DisplayName("Positive test. When we have correct list of Agreement then return correct list of AgreementDto")
    void testAgreementsToAgreementsDTO() {
        UUID managerId = UUID.randomUUID();
        List<Agreement> agreementList = new ArrayList<>();
        agreementList.add(EntityCreator.getAgreement(managerId));

        List<AgreementDTO> agreementDTOList = agreementMapper.agreementsToAgreementsDTO(agreementList);
        compareManagerListWithListDto(agreementList, agreementDTOList);
    }

    @Test
    @DisplayName("Positive test. Check to init correct current date")
    void testCreateToEntity() {
        CreateAgreementDTO dto = DTOCreator.getAgreementToCreateWithCreateDate();
        Agreement agreement = agreementMapper.createToEntity(dto);

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String current = date.format(currentDate);
        String agreementDate = date.format(agreement.getCreatedAt());

        assertNull(dto.getCreatedAt());
        assertNotNull(agreement.getCreatedAt());
        assertEquals(current, agreementDate);
    }

    private void compareEntityWithDto(Agreement agreement, AgreementDTO agreementDTO){
        assertAll(
                () -> assertEquals(agreement.getId().toString(), agreementDTO.getId()),
                () -> assertEquals(Double.toString(agreement.getInterestRate()), agreementDTO.getInterestRate()),
                () -> assertEquals(agreement.getStatus().toString(), agreementDTO.getStatus()),
                () -> assertEquals(Double.toString(agreement.getSum()), agreementDTO.getSum()),
                () -> assertEquals(agreement.getCreatedAt(), agreementDTO.getCreatedAt()),
                () -> assertEquals(agreement.getUpdatedAt(), agreementDTO.getUpdatedAt())
        );
    }

    private void compareManagerListWithListDto(List<Agreement> agreementList, List<AgreementDTO> agreementDTOList){
        assertEquals(agreementList.size(), agreementDTOList.size());
        for(int i = 0; i < agreementList.size(); i++){
            compareEntityWithDto(agreementList.get(i), agreementDTOList.get(i));
        }
    }
}