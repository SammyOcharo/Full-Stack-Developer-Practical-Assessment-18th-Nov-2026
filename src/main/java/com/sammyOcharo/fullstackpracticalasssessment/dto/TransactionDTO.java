package com.sammyOcharo.fullstackpracticalasssessment.dto;

public record TransactionDTO(
    Double amount,
    String senderAccountNumber,
    String receiverAccountNumber

) {
}
