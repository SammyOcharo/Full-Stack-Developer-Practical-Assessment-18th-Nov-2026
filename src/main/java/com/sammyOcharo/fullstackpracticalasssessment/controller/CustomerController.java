package com.sammyOcharo.fullstackpracticalasssessment.controller;


import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CreateCustomerResponse;
import com.sammyOcharo.fullstackpracticalasssessment.dto.CustomerResponse;
import com.sammyOcharo.fullstackpracticalasssessment.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("create/")
    private ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO){
        return customerService.createCustomer(createCustomerDTO);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<CustomerResponse> getCustomerDetails(@PathVariable("id") Long customerId) {
        return customerService.getCustomer(customerId);
    }

}
