package com.sammyOcharo.fullstackpracticalasssessment.exception;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String message) {
        super(message);
    }
}
