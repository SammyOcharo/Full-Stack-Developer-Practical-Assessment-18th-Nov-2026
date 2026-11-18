package com.sammyOcharo.fullstackpracticalasssessment.dto;

import java.util.UUID;

public record TransactionResponseDTO(
        String ResponseMessage,
        UUID transactionId,
        Double senderAccountBalance
) {
}
