package com.sammyOcharo.fullstackpracticalasssessment.controller;

import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionResponseDTO;
import com.sammyOcharo.fullstackpracticalasssessment.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer_transaction/")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transfer_funds/")
    private ResponseEntity<TransactionResponseDTO> customerTransferFunds(@RequestBody TransactionDTO transactionDTO){

        return  transactionService.customerTransferFunds(transactionDTO);
    }
}
