package com.example.bankapplication.controller.handler;

import com.example.bankapplication.dto.ErrorDTO;
import com.example.bankapplication.service.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BankApplicationExceptionHandlerTest {

    @Mock
    private AccountNotFoundException accountNotFoundException;

    @Mock
    private AgreementNotFoundException agreementNotFoundException;

    @Mock
    private ClientNotFoundException clientNotFoundException;

    @Mock
    private ManagerNotFoundException managerNotFoundException;

    @Mock
    private ProductNotFoundException productNotFoundException;

    @Mock
    private TransactionNotFoundException transactionNotFoundException;

    @Mock
    private TaxCodeExistsException taxCodeExistsException;

    @Mock
    private SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException;

    @Mock
    private NullPointerException nullPointerException;

    @Mock
    private SQLException sqlException;

    @Mock
    private IllegalArgumentException illegalArgumentException;

    @Mock
    private NegativeDataException negativeDataException;

    @InjectMocks
    private BankApplicationExceptionHandler bankApplicationExceptionHandler;

    @Test
    void testHandleAccountNotFoundException() {
        when(accountNotFoundException.getMessage()).thenReturn("Account not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleAccountNotFoundException(accountNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Account not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleAgreementNotFoundException() {
        when(agreementNotFoundException.getMessage()).thenReturn("Agreement not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleAgreementNotFoundException(agreementNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Agreement not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleClientNotFoundException() {
        when(clientNotFoundException.getMessage()).thenReturn("Client not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleClientNotFoundException(clientNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Client not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleManagerNotFoundException() {
        when(managerNotFoundException.getMessage()).thenReturn("Manager not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleManagerNotFoundException(managerNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Manager not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleProductNotFoundException() {
        when(productNotFoundException.getMessage()).thenReturn("Product not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleProductNotFoundException(productNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Product not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleTransactionNotFoundException() {
        when(transactionNotFoundException.getMessage()).thenReturn("Transaction not found");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleTransactionNotFoundException(transactionNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Transaction not found", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandlerTaxCodeExistsException() {
        when(taxCodeExistsException.getMessage()).thenReturn("Tax code already exists");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handlerTaxCodeExistsException(taxCodeExistsException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Tax code already exists", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleSQLIntegrityConstraintViolationException() {
        when(sqlIntegrityConstraintViolationException.getMessage()).thenReturn("Integrity constraint violation");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleSQLIntegrityConstraintViolationException(sqlIntegrityConstraintViolationException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Integrity constraint violation", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleNullPointerException() {
        when(nullPointerException.getMessage()).thenReturn("NullPointerException occurred");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleNullPointerException(nullPointerException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("NullPointerException occurred", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleSQLException() {
        when(sqlException.getMessage()).thenReturn("SQL Exception occurred");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleSQLException(sqlException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("SQL Exception occurred", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleIllegalArgumentException() {
        when(illegalArgumentException.getMessage()).thenReturn("Illegal Argument Exception occurred");
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleIllegalArgumentException(illegalArgumentException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Illegal Argument Exception occurred", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void testHandleNegativeDataException(){
        when(negativeDataException.getMessage()).thenReturn(ErrorMessage.NEGATIVE_DATA);
        ResponseEntity<ErrorDTO> responseEntity = bankApplicationExceptionHandler
                .handleNegativeDataException(negativeDataException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(ErrorMessage.NEGATIVE_DATA, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }
}