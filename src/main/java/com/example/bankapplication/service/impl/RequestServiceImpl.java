package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.*;
import com.example.bankapplication.entity.enums.ProductStatus;
import com.example.bankapplication.repository.*;
import com.example.bankapplication.service.RequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

/**
 * The `RequestServiceImpl` class is an implementation of the `RequestService` interface.
 * It provides methods for performing various operations related to requests.
 *
 * @Service: This annotation is used to indicate that this class is a service component in the Spring framework.
 * @RequiredArgsConstructor: This annotation is from the Lombok library and generates a constructor with required arguments
 * for the final fields. It allows us to inject dependencies using constructor injection.
 * @Slf4j: This annotation is from the Lombok library and generates a logger field for logging.
 * @Transactional: This annotation is used in Spring to define transactional boundaries for methods or classes.
 * When applied to a method or class, it indicates that a transaction should be created for the annotated method
 * or all methods within the annotated class.
 * Transactional boundaries ensure that a group of operations are executed as a single atomic unit.
 * If an exception occurs during the execution of the annotated method or any method within the annotated class,
 * the transaction will be rolled back, and any changes made within the transaction will be undone.
 * By using the `@Transactional` annotation, we can manage transactions declaratively without having
 * to write explicit transaction management code. Spring takes care of creating, committing,
 * or rolling back transactions based on the annotated method's execution.
 * It is important to note that the `@Transactional` annotation should be applied to methods that modify data
 * or perform multiple database operations to ensure data integrity and consistency.
 * <p>
 * AccountFindIdsRepository findIdsRepository: This field is used to find account IDs by product ID and status.
 * <p>
 * AgreementFindIdsRepository agFindIdsRepository: This field is used to find agreement IDs by manager ID or client ID.
 * <p>
 * ClientFindRepository clientFindRepository: This field is used to find client information based on certain conditions.
 * <p>
 * ManagerFindRepository managerFindRepository: This field is used to find manager information based on certain conditions.
 * <p>
 * ProductInfoRepository productInfoRepository: This field is used to find product information based on certain conditions.
 * <p>
 * findAccountsByProductIdAndStatus(UUID productId, ProductStatus status): This method finds account IDs based on the specified
 * product ID and product status. It returns a collection of `AccountIdDTO` objects.
 * <p>
 * findAgreementsByManagerId(UUID managerId): This method finds agreement IDs based on the specified manager ID.
 * It returns a collection of `AgreementIdDTO` objects.
 * <p>
 * findAgreementByClientId(UUID clientId): This method finds agreement IDs based on the specified client ID.
 * It returns a collection of `AgreementIdDTO` objects.
 * <p>
 * findClientsWhereBalanceMoreThan(Double balance): This method finds client information where the balance is more than the specified amount.
 * It returns a collection of `ClientInfoDTO` objects.
 * <p>
 * findAllManagersSortedByProductQuantity(): This method finds all managers and sorts them based on the quantity of products they manage.
 * It returns a collection of `ManagerInfoDTO` objects.
 * <p>
 * findAllChangedProducts(): This method finds all products that have been changed.
 * It returns a collection of `ProductDTO` objects.
 * <p>
 * The `RequestServiceImpl` class implements the `RequestService` interface,
 * which defines the contract for performing operations on requests.
 * By implementing this interface, the class provides the necessary business logic for request-related operations.
 * <p>
 * With the `RequestServiceImpl` class, we can find accounts by product ID and status, find agreements by manager ID or client ID,
 * find clients with a balance more than a specified amount, find managers sorted by product quantity, and find changed products.
 * It uses various repositories to access the required data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final AccountFindIdsRepository findIdsRepository;
    private final AgreementFindIdsRepository agFindIdsRepository;
    private final ClientFindRepository clientFindRepository;
    private final ManagerFindRepository managerFindRepository;
    private final ProductInfoRepository productInfoRepository;

    @Override
    @Transactional
    public Collection<AccountIdDTO> findAccountsByProductIdAndStatus(UUID productId, ProductStatus status) {
        log.info("Find accounts by productId and productStatus");
        log.info("UUID: " + productId + ", Status: " + status);
        return findIdsRepository.findAccountsByProductIdAndStatus(productId, status);
    }

    @Override
    @Transactional
    public Collection<AgreementIdDTO> findAgreementsByManagerId(UUID managerId) {
        log.info("Find agreements by managerId {}", managerId);
        return agFindIdsRepository.findAgreementsByManagerId(managerId);
    }

    @Override
    @Transactional
    public Collection<AgreementIdDTO> findAgreementByClientId(UUID clientId) {
        log.info("Find agreements by clientId {}", clientId);
        return agFindIdsRepository.findAgreementByClientId(clientId);
    }

    @Override
    @Transactional
    public Collection<ClientInfoDTO> findClientsWhereBalanceMoreThan(Double balance) {
        log.info("Find clients with balance more than {}", balance);
        return clientFindRepository.findClientWhereBalanceMoreThan(balance);
    }

    @Override
    @Transactional
    public Collection<ManagerInfoDTO> findAllManagersSortedByProductQuantity() {
        log.info("Find managers sorted by product quantity");
        return managerFindRepository.findAllManagersSortedByProductQuantity();
    }

    @Override
    @Transactional
    public Collection<ProductDTO> findAllChangedProducts() {
        log.info("Find changed products");
        return productInfoRepository.findAllChangedProducts();
    }
}