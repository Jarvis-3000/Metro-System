package com.example.application.dtos.bookingDTO;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingRequestTests {

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Test BookingRequest with valid data")
  public void testBookingRequestWithValidData() {
    // Given
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setOriginStationId("Station001");
    bookingRequest.setDestinationStationId("Station002");
    bookingRequest.setDateTime(LocalDateTime.now().plusHours(1).toString());

    // When
    Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

    // Then
    assertTrue(violations.isEmpty(), "Validation should pass with valid data");
  }

  @Test
  @DisplayName("Test BookingRequest with blank origin station id")
  public void testBookingRequestWithBlankOriginStationId() {
    // Given
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setOriginStationId("");
    bookingRequest.setDestinationStationId("Station002");
    bookingRequest.setDateTime(LocalDateTime.now().plusHours(1).toString());

    // When
    Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

    // Then
    assertFalse(violations.isEmpty(), "Validation should fail with blank origin station id");
    assertEquals(1, violations.size(), "Only one validation error expected for blank origin station id");
  }

  @Test
  @DisplayName("Test BookingRequest with blank destination station id")
  public void testBookingRequestWithBlankDestinationStationId() {
    // Given
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setOriginStationId("Station001");
    bookingRequest.setDestinationStationId("");
    bookingRequest.setDateTime(LocalDateTime.now().plusHours(1).toString());

    // When
    Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

    // Then
    assertFalse(violations.isEmpty(), "Validation should fail with blank destination station id");
    assertEquals(1, violations.size(), "Only one validation error expected for blank destination station id");
  }

  @Test
  @DisplayName("Test BookingRequest with null date time")
  public void testBookingRequestWithNullDateTime() {
    // Given
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setOriginStationId("Station001");
    bookingRequest.setDestinationStationId("Station002");
    bookingRequest.setDateTime(null);

    // When
    Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

    // Then
    assertFalse(violations.isEmpty(), "Validation should fail with null date time");
    assertEquals(1, violations.size(), "Only one validation error expected for null date time");
  }

  @Test
  @DisplayName("Test BookingRequest with past date time")
  public void testBookingRequestWithPastDateTime() {
    // Given
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setOriginStationId("Station001");
    bookingRequest.setDestinationStationId("Station002");
    bookingRequest.setDateTime(LocalDateTime.now().minusHours(1).toString());

    // When
    Set<ConstraintViolation<BookingRequest>> violations = validator.validate(bookingRequest);

    // Then
    assertFalse(violations.isEmpty(), "Validation should fail with past date time");
    assertEquals(1, violations.size(), "Only one validation error expected for past date time");
  }

}
