package com.example.bankapplication.controller.handler;

import com.example.bankapplication.dto.ErrorDTO;
import com.example.bankapplication.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * The @Slf4j annotation is used for automatic generation of a logger field in the class,
 * allowing logging statements to be written using the logger object.
 * <p>
 * The @ControllerAdvice annotation marks this class as an exception handler that can be applied globally
 * across multiple controllers.
 * <p>
 * The @ExceptionHandler annotation is used to define exception handling methods that are invoked
 * when specific exceptions occur, allowing customized error handling and response generation.
 * <p>
 * The ResponseEntity<ErrorDTO> is a generic class provided by Spring Framework that represents
 * the HTTP response containing a body of type ErrorDTO. It allows you to customize
 * the response status code, headers, and body.
 */

@Slf4j
@ControllerAdvice
public class BankApplicationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleAccountNotFoundException(AccountNotFoundException ex) {
        log.error(ErrorMessage.ACCOUNT_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleAgreementNotFoundException(AgreementNotFoundException ex) {
        log.error(ErrorMessage.AGREEMENT_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleClientNotFoundException(ClientNotFoundException ex) {
        log.error(ErrorMessage.CLIENT_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleManagerNotFoundException(ManagerNotFoundException ex) {
        log.error(ErrorMessage.Manager_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException ex) {
        log.error(ErrorMessage.PRODUCT_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        log.error(ErrorMessage.TRANSACTION_NOT_FOUND, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handlerTaxCodeExistsException(TaxCodeExistsException ex) {
        log.error(ErrorMessage.TAX_CODE_EXISTS, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex) {
        log.error(ErrorMessage.TAX_CODE_EXISTS, ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException", ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleSQLException(SQLException ex) {
        log.error("SQLException", ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException", ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleNegativeDataException(NegativeDataException ex){
        log.error("NegativeDataException", ex);
        var error = new ErrorDTO(HttpURLConnection.HTTP_INTERNAL_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }
}
