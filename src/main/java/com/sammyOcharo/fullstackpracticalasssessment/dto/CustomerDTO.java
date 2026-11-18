package com.sammyOcharo.fullstackpracticalasssessment.dto;

import lombok.*;

@Builder
public record CustomerDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String accountNumber,
        Double accountBalance
) {
}
