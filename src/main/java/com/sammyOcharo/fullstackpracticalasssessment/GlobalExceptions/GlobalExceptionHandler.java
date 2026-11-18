package com.sammyOcharo.fullstackpracticalasssessment.GlobalExceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sammyOcharo.fullstackpracticalasssessment.dto.ErrorResponseDTO;
import com.sammyOcharo.fullstackpracticalasssessment.exception.AccountNumberAlreadyExistException;
import com.sammyOcharo.fullstackpracticalasssessment.exception.InsufficientFundsException;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserAlreadyExistException;
import com.sammyOcharo.fullstackpracticalasssessment.exception.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExists(UserAlreadyExistException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "User already exists",
                "409"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponseDTO> InsufficientFundsException(UserAlreadyExistException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Insufficient funds in your account",
                "409"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AccountNumberAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> accountNumberAlreadyExists(UserAlreadyExistException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Account Number already exists",
                "409"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorResponseDTO> userDoesNotExist(UserAlreadyExistException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "User does not exists",
                "404"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException() {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "An unexpected error occurred",
                "500"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}