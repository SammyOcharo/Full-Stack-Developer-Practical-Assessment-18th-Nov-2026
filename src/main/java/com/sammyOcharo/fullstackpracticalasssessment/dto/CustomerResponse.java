package com.sammyOcharo.fullstackpracticalasssessment.dto;

public record CustomerResponse(
        CustomerDTO customerDTO,
        String responseMessage,
        Integer responseStatusCode
) {


}
