package com.sammyOcharo.fullstackpracticalasssessment.exception;

public class AccountNumberAlreadyExistException extends RuntimeException {
    public AccountNumberAlreadyExistException(String message) {
        super(message);
    }
}
