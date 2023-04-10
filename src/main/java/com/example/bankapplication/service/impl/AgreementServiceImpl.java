package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.AgreementDTO;
import com.example.bankapplication.dto.AgreementListDTO;
import com.example.bankapplication.dto.CreateAgreementDTO;
import com.example.bankapplication.entity.enums.AgreementStatus;
import com.example.bankapplication.mapper.AgreementMapper;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.AgreementRepository;
import com.example.bankapplication.repository.ProductRepository;
import com.example.bankapplication.service.AgreementService;
import com.example.bankapplication.service.exception.AccountNotFoundException;
import com.example.bankapplication.service.exception.AgreementNotFoundException;
import com.example.bankapplication.service.exception.ErrorMessage;
import com.example.bankapplication.service.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgreementServiceImpl implements AgreementService {
    private final AgreementMapper agreementMapper;
    private final AgreementRepository agreementRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    @Override
    public AgreementListDTO getAll() {
        log.info("Get all agreements");
        return new AgreementListDTO(agreementMapper.agreementsToAgreementsDTO(agreementRepository.findAll()));
    }

    @Override
    public AgreementDTO getAgreementById(UUID id) {
        log.info("Get agreement by id {}", id);
        return agreementMapper.toDTO(agreementRepository.findAgreementById(id).orElseThrow(
                () -> new AgreementNotFoundException(ErrorMessage.AGREEMENT_NOT_FOUND)
        ));
    }

    @Override
    public AgreementDTO createAgreement(CreateAgreementDTO dto) {
        log.info("Create new agreement");

        var productId = dto.getProductId();
        log.info("Product id : " + dto.getProductId());
        var product = productRepository.findProductById(productId).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );
        log.info("Product name: " + product.getName());

        var accountId = dto.getAccountId();
        log.info("Account id : " + dto.getAccountId());
        var account = accountRepository.findAccountById(accountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );
        log.info("Account name: " + account.getName());

        var agreement = agreementMapper.createToEntity(dto);
        agreement.setProduct(product);
        agreement.setAccount(account);
        var result = agreementRepository.save(agreement);
        return agreementMapper.toDTO(result);
    }

    @Override
    public AgreementDTO editAgreementById(UUID id, CreateAgreementDTO dto) {
        log.info("Edit agreement by id {}", id);

        var agreement = agreementRepository.findAgreementById(id).orElseThrow(
                () -> new AgreementNotFoundException(ErrorMessage.AGREEMENT_NOT_FOUND)
        );

        var accountId = dto.getAccountId();
        var account = accountRepository.findAccountById(accountId).orElseThrow(
                () -> new AccountNotFoundException(ErrorMessage.ACCOUNT_NOT_FOUND)
        );

        var productId = dto.getProductId();
        var product = productRepository.findProductById(productId).orElseThrow(
                () -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND)
        );

        agreement.setInterestRate(Double.parseDouble(dto.getInterestRate()));
        agreement.setStatus(AgreementStatus.valueOf(dto.getStatus()));
        agreement.setSum(Double.parseDouble(dto.getSum()));
        agreement.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        agreement.setAccount(account);
        agreement.setProduct(product);

        var result = agreementRepository.save(agreement);
        return agreementMapper.toDTO(result);
    }

    @Override
    public void deleteAgreementById(UUID id) {
        log.info("Deleting agreement {}", id);
        var agreement = agreementRepository.findAgreementById(id);
        if(agreement.isPresent())
            agreementRepository.deleteById(id);
        else throw new AgreementNotFoundException(ErrorMessage.AGREEMENT_NOT_FOUND);
    }
}
