package com.example.application.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.application.dtos.bookingDTO.BookingRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Booking", description = "User Metro Train Booking Endpoints")
public interface BookingApi {
  @Operation(summary = "Book", description = "Book a metro train between Origin Station and Destinatin Station")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully booked a metro train and Deducted money from User Account"),
      @ApiResponse(responseCode = "400", description = "Invalid arguments or Balance not sufficient")
  })
  public ResponseEntity<?> book(@Valid @RequestBody BookingRequest bookingRequest,
      HttpServletRequest request);

  @Operation(summary = "Cancel Booking", description = "Cancel a booking of train by BookingId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully cancelled the booking and refunded money in User Account"),
      @ApiResponse(responseCode = "400", description = "Invalid Id"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<?> cancelBooking(@Valid @PathVariable String id,
      HttpServletRequest request);
}
