package com.sammyOcharo.fullstackpracticalasssessment.service;

import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerResponse;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CustomerResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerResponse> createCustomer(CreateCustomerDTO createCustomerDTO);

    ResponseEntity<CustomerResponse> getCustomer(Long customerId);
}
