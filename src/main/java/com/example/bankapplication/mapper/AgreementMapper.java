package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.util.List;

/**
 * The `AgreementMapper` interface is a mapper interface using the MapStruct library. It provides mapping methods
 * to convert between `Agreement` entities and `AgreementDTO` data transfer objects.
 *
 * `@Mapper(componentModel = "spring", imports = Timestamp.class)`: This annotation is from the MapStruct library
 * and specifies that this interface should be treated as a mapper component.
 * The `componentModel="spring"` attribute indicates that Spring should manage the lifecycle of the mapper bean.
 * The `imports = Timestamp.class` attribute specifies that the `Timestamp` class should be imported for usage
 * in mapping expressions.
 *
 * `@Mapping(source = "agreement.product.id", target = "productId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `productId` property
 * of the `Agreement` entity and the `productId` property of the `AgreementDTO`. It maps the value from `agreement.product.id`
 * to `productId` during the conversion.
 *
 * `@Mapping(source = "agreement.account.id", target = "accountId")`:
 * This annotation is used on the `toDTO` method to specify a mapping between the `accountId` property
 * of the `Agreement` entity and the `accountId` property of the `AgreementDTO`. It maps the value from `agreement.account.id`
 * to `accountId` during the conversion.
 *
 * `AgreementDTO toDTO(Agreement agreement)`:
 * This method maps an `Agreement` entity to an `AgreementDTO`.
 * It uses the `@Mapping` annotation to define the mapping between properties.
 *
 * `Agreement toEntity(AgreementDTO agreementDTO)`: This method maps an `AgreementDTO` to an `Agreement` entity.
 *
 * `List<AgreementDTO> agreementsToAgreementsDTO(List<Agreement> agreements)`:
 * This method maps a list of `Agreement` entities to a list of `AgreementDTO` objects.
 *
 * `@Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")`:
 * This annotation is used on the `createToEntity` method to set the `createdAt` property of the `Agreement` entity.
 * It uses a mapping expression to create a new `Timestamp` object representing the current system time.
 *
 * `Agreement createToEntity(CreateAgreementDTO dto)`:
 * This method maps a `CreateAgreementDTO` to an `Agreement` entity, including setting the `createdAt` property using
 * the mapping expression defined by the `@Mapping` annotation.
 */
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
