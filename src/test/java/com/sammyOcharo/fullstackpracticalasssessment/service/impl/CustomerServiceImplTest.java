package com.sammyOcharo.fullstackpracticalasssessment.service.impl;


import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CustomerResponse;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserAlreadyExistException;
import com.sammyOcharo.fullstackpracticalasssessment.model.CustomerEntity;
import com.sammyOcharo.fullstackpracticalasssessment.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.never;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CreateCustomerDTO createCustomerDTO;
    private CustomerEntity sampleCustomerEntity;

    @BeforeEach
    void setUp() {
        createCustomerDTO = new CreateCustomerDTO(
                "Sammy",
                "Ocharo",
                "sammy@example.com",
                "0712345678"
        );

        sampleCustomerEntity = CustomerEntity.builder()
                .firstName("Sammy")
                .lastName("Ocharo")
                .email("sammy@example.com")
                .phoneNumber("0712345678")
                .accountNumber("123456789012")
                .accountBalance(0.0)
                .isActive(Boolean.TRUE)
                .build();
    }

    @Test
    void createCustomer_Success() {
        when(customerRepository.existsByEmail(createCustomerDTO.email())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(createCustomerDTO.phoneNumber())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(sampleCustomerEntity);

        ResponseEntity<CustomerResponse> response = customerService.createCustomer(createCustomerDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    void createCustomer_ThrowsException_WhenEmailAlreadyExists() {
        when(customerRepository.existsByEmail(createCustomerDTO.email())).thenReturn(true);
        UserAlreadyExistException exception = assertThrows(
                UserAlreadyExistException.class,
                () -> customerService.createCustomer(createCustomerDTO)
        );

        assertEquals("Customer with email sammy@example.com already exists", exception.getMessage());
    }


}