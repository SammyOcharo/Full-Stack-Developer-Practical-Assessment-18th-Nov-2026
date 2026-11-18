package com.sammyOcharo.fullstackpracticalasssessment.service.impl;

import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionResponseDTO;
import com.sammyOcharo.fullstackpracticalasssessment.exception.InsufficientFundsException;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserDoesNotExistException;
import com.sammyOcharo.fullstackpracticalasssessment.model.CustomerEntity;
import com.sammyOcharo.fullstackpracticalasssessment.model.TransactionEntity;
import com.sammyOcharo.fullstackpracticalasssessment.model.TransactionStatus;
import com.sammyOcharo.fullstackpracticalasssessment.repository.CustomerRepository;
import com.sammyOcharo.fullstackpracticalasssessment.repository.TransactionRepository;
import com.sammyOcharo.fullstackpracticalasssessment.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public ResponseEntity<TransactionResponseDTO> customerTransferFunds(TransactionDTO transactionDTO) {
        if (transactionDTO.amount() == null || transactionDTO.amount() <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }
        if (transactionDTO.senderAccountNumber().equals(transactionDTO.receiverAccountNumber())) {
            throw new IllegalArgumentException("Cannot transfer funds to the same account");
        }

        CustomerEntity sender = customerRepository.findByAccountNumber(transactionDTO.senderAccountNumber())
                .orElseThrow(() -> new UserDoesNotExistException(
                        "Sender account " + transactionDTO.senderAccountNumber() + " does not exist"));

        CustomerEntity receiver = customerRepository.findByAccountNumber(transactionDTO.receiverAccountNumber())
                .orElseThrow(() -> new UserDoesNotExistException(
                        "Receiver account " + transactionDTO.receiverAccountNumber() + " does not exist"));

        if (sender.getAccountBalance() < transactionDTO.amount()) {
            log.warn("Insufficient funds for account {}", sender.getAccountNumber());
            throw new InsufficientFundsException("Insufficient funds for this transaction");
        }

        sender.setAccountBalance(sender.getAccountBalance() - transactionDTO.amount());
        receiver.setAccountBalance(receiver.getAccountBalance() + transactionDTO.amount());

        customerRepository.save(sender);
        customerRepository.save(receiver);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setCustomer(sender);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        TransactionEntity savedTransaction = transactionRepository.save(transaction);

        log.info("Transferred {} from {} to {}. Transaction ID: {}",
                transactionDTO.amount(),
                sender.getAccountNumber(),
                receiver.getAccountNumber(),
                savedTransaction.getTransaction_id());

        TransactionResponseDTO response = new TransactionResponseDTO(
                "Funds transferred successfully",
                savedTransaction.getTransaction_id(),
                sender.getAccountBalance()
        );

        return ResponseEntity.ok(response);
    }
}