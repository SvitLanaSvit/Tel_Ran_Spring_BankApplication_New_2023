package com.example.bankapplication.repository;

import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.Agreement;
import com.example.bankapplication.entity.Product;
import com.example.bankapplication.entity.enums.AgreementStatus;
import com.example.bankapplication.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for AgreementRepository")
class AgreementRepositoryTest {

    @Mock
    private AgreementRepository agreementRepository;

    @Test
    @DisplayName("Positive test. Find all agreements.")
    void testFindAll() {
        Account account = mock(Account.class);
        Product product = mock(Product.class);
        List<Agreement> agreements = new ArrayList<>(List.of(
                new Agreement(UUID.randomUUID(), 0.05, AgreementStatus.ACTIVE, 10.0, null, null, product, account)
        ));

        when(agreementRepository.findAll()).thenReturn(agreements);

        List<Agreement> foundAgreements = agreementRepository.findAll();

        assertEquals(agreements.size(), foundAgreements.size());
        verify(agreementRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Positive test. Find agreement by id.")
    void testFindAgreementById() {
        Agreement agreement = EntityCreator.getAgreement(UUID.randomUUID());

        when(agreementRepository.findAgreementById(agreement.getId())).thenReturn(Optional.of(agreement));

        Optional<Agreement> foundAgreement = agreementRepository.findAgreementById(agreement.getId());
        assertTrue(foundAgreement.isPresent());
        assertEquals(agreement, foundAgreement.get());

        verify(agreementRepository, times(1)).findAgreementById(agreement.getId());
    }
}