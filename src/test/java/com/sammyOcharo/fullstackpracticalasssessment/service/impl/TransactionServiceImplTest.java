package com.sammyOcharo.fullstackpracticalasssessment.service.impl;

import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionDTO;
import com.sammyOcharo.fullstackpracticalasssessment.dto.TransactionResponseDTO;
import com.sammyOcharo.fullstackpracticalasssessment.exception.InsufficientFundsException;
import com.sammyOcharo.fullstackpracticalasssessment.model.CustomerEntity;
import com.sammyOcharo.fullstackpracticalasssessment.model.TransactionEntity;
import com.sammyOcharo.fullstackpracticalasssessment.model.TransactionStatus;
import com.sammyOcharo.fullstackpracticalasssessment.repository.CustomerRepository;
import com.sammyOcharo.fullstackpracticalasssessment.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private CustomerEntity sender;
    private CustomerEntity receiver;

    @BeforeEach
    void setUp() {
        sender = CustomerEntity.builder()
                .firstName("Sammy")
                .lastName("Ocharo")
                .email("sammy@example.com")
                .phoneNumber("0712345678")
                .accountNumber("100000000001")
                .accountBalance(5000.0)
                .isActive(Boolean.TRUE)
                .build();

        receiver = CustomerEntity.builder()
                .firstName("Steve")
                .lastName("Obanyi")
                .email("j@example.com")
                .phoneNumber("0798765432")
                .accountNumber("100000000002")
                .accountBalance(1000.0)
                .isActive(Boolean.TRUE)
                .build();
    }

    @Test
    void customerTransferFunds_Success() {
        TransactionDTO dto = new TransactionDTO(100.00, "100000000001", "100000000002");
        UUID transactionId = UUID.randomUUID();

        TransactionEntity savedTransaction = new TransactionEntity();
        savedTransaction.setTransaction_id(transactionId);
        savedTransaction.setCustomer(sender);
        savedTransaction.setTransactionStatus(TransactionStatus.COMPLETED);

        when(customerRepository.findByAccountNumber("100000000001")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("100000000002")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedTransaction);
        ResponseEntity<TransactionResponseDTO> response = transactionService.customerTransferFunds(dto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().transactionId());


        verify(customerRepository, times(1)).save(sender);
        verify(customerRepository, times(1)).save(receiver);
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void customerTransferFunds_ThrowsException_WhenAmountIsZeroOrNegative() {
        TransactionDTO zeroAmountDto = new TransactionDTO(0.00, "100000000001", "100000000002");
        TransactionDTO negativeAmountDto = new TransactionDTO(-200.00, "100000000001", "100000000002");
        TransactionDTO nullAmountDto = new TransactionDTO(null, "100000000001", "100000000002");
        assertThrows(IllegalArgumentException.class, () -> transactionService.customerTransferFunds(zeroAmountDto));
        assertThrows(IllegalArgumentException.class, () -> transactionService.customerTransferFunds(negativeAmountDto));
        assertThrows(IllegalArgumentException.class, () -> transactionService.customerTransferFunds(nullAmountDto));
        verifyNoInteractions(customerRepository, transactionRepository);
    }

    @Test
    void customerTransferFunds_ThrowsException_WhenTransferringToSameAccount() {
        TransactionDTO sameAccountDto = new TransactionDTO(100.00, "100000000001", "100000000001");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.customerTransferFunds(sameAccountDto)
        );

        assertEquals("Cannot transfer funds to the same account", exception.getMessage());
        verifyNoInteractions(customerRepository, transactionRepository);
    }
}