package com.sammyOcharo.fullstackpracticalasssessment.dto;

public record ErrorResponseDTO(
        String errorMessage,
        String errorStatusCode
) {

}
