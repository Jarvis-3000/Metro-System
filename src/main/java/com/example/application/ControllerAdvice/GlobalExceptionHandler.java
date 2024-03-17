package com.example.application.ControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.dtos.errorDTO.ErrorResponse;
import com.example.application.exceptions.InsufficientBalanceException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

// Catch exceptions across the application and respond to client just like RestCOntroller
// instead it is not sending REST data but Error Response
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();

    // Get all field errors from the exception
    BindingResult bindingResult = ex.getBindingResult();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    // Create the error response with the detailed message
    ErrorResponse errorResponse = new ErrorResponse(errorMap, HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
    // Create a map to hold the error message
    Map<String, String> errorMap = new HashMap<>();

    errorMap.put("error", "Response status: " + ex.getMessage());

    // Create the error response with detailed error message and status code
    ErrorResponse errorResponse = new ErrorResponse(errorMap, ex.getStatusCode());

    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
    // Create a map to hold the error message
    Map<String, String> errorMap = new HashMap<>();

    errorMap.put("error", "" + ex.getMessage());

    // Create the error response with detailed error message and status code
    ErrorResponse errorResponse = new ErrorResponse(errorMap, HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ErrorResponse> handleDateTimeParsingException(DateTimeParseException ex) {
    // Create a map to hold the error message
    Map<String, String> errorMap = new HashMap<>();

    errorMap.put("error", "Invalid Date Time format. Must be (yyyy-MM-dd'T'HH:mm:ss)");

    // Create the error response with detailed error message and status code
    ErrorResponse errorResponse = new ErrorResponse(errorMap, HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    // Create a map to hold the error message
    Map<String, String> errorMap = new HashMap<>();

    errorMap.put("error", ex.getMessage()); // Extract the exception message

    // Create the error response with detailed error message and status code
    ErrorResponse errorResponse = new ErrorResponse(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
