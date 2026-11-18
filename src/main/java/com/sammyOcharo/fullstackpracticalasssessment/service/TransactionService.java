package com.sammyOcharo.fullstackpracticalasssessment.service;

import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionResponseDTO;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<TransactionResponseDTO> customerTransferFunds(TransactionDTO transactionDTO);
}
