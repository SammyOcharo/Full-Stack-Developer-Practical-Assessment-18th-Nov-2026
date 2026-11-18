package com.sammyOcharo.fullstackpracticalasssessment.dto;

import jakarta.validation.constraints.Email;
import lombok.NonNull;

public record CreateCustomerResponse(
        @NonNull
        String firstName,
        @NonNull
        String lastName,
        @Email
        String email,
        @NonNull
        String phoneNumber
) {
}
