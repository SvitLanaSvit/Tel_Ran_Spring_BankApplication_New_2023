package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", /*uses = UuidMapper.class,*/ imports = Timestamp.class)
public interface AgreementMapper {
    @Mapping(source = "agreement.product.id", target = "productId")
    @Mapping(source = "agreement.account.id", target = "accountId")
    AgreementDTO toDTO(Agreement agreement);
    Agreement toEntity(AgreementDTO agreementDTO);
    List<AgreementDTO> agreementsToAgreementsDTO(List<Agreement> agreements);
    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Agreement createToEntity(CreateAgreementDTO dto);
}
