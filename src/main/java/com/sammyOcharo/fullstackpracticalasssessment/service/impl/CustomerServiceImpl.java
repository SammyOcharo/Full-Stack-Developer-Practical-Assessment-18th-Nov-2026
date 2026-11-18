package com.sammyOcharo.fullstackpracticalasssessment.service.impl;

import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CustomerDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CustomerResponse;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserAlreadyExistException;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserDoesNotExistException;
import com.sammyOcharo.fullstackpracticalasssessment.model.CustomerEntity;
import com.sammyOcharo.fullstackpracticalasssessment.repository.CustomerRepository;
import com.sammyOcharo.fullstackpracticalasssessment.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CreateCustomerDTO createCustomerDTO) {
        if(customerRepository.existsByEmail(createCustomerDTO.email())){
            throw new UserAlreadyExistException("Customer with email "+ createCustomerDTO.email() + " already exists");
        }

        if(customerRepository.existsByPhoneNumber(createCustomerDTO.phoneNumber())){
            throw new UserAlreadyExistException("Customer with phone number "+ createCustomerDTO.phoneNumber() + " already exists");
        }

        CustomerEntity customerEntity = dtoToEntityMapper(createCustomerDTO);

        log.info("The dto has been mapped to the Customer Entity");

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Customer saved successfully with account number: {}", savedCustomer.getAccountNumber());

        ;

        CustomerResponse response = new CustomerResponse(
                entityToDtoMapper(savedCustomer),
                "Customer created successfully",
                201
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    private CustomerDTO entityToDtoMapper(CustomerEntity savedCustomer) {
        return CustomerDTO.builder()
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .email(savedCustomer.getEmail())
                .phoneNumber(savedCustomer.getPhoneNumber())
                .accountNumber(savedCustomer.getAccountNumber())
                .accountBalance(savedCustomer.getAccountBalance())
                .build();
    }

    private String generateUniqueAccountNumber() {
        SecureRandom random = new SecureRandom();
        String accountNumber;
        do {
            long number = 100_000_000_000L + (Math.abs(random.nextLong()) % 900_000_000_000L);
            accountNumber = String.valueOf(number);
        } while (customerRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }


    private CustomerEntity dtoToEntityMapper(CreateCustomerDTO createCustomerDTO) {
        return CustomerEntity.builder()
                .firstName(createCustomerDTO.firstName())
                .lastName(createCustomerDTO.lastName())
                .email(createCustomerDTO.email())
                .phoneNumber(createCustomerDTO.phoneNumber())
                .isActive(Boolean.TRUE)
                .build();

    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomer(Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("Customer with id {} does not exist", customerId);
                    return new UserDoesNotExistException("Customer with id " + customerId + " does not exist");
                });

        CustomerDTO customerDTO = entityToDtoMapper(customer);

        CustomerResponse customerResponse = new CustomerResponse(
                customerDTO,
                "Customer retrieved successfully",
                200
        );

        return ResponseEntity.ok(customerResponse);
    }
}
