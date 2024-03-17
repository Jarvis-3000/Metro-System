package com.example.application.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.application.dtos.bookingDTO.BookingDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Booking History", description = "User Booking History Endpoints")
public interface BookingHistoryApi {
  @Operation(summary = "Admin can get Booking History of All Users", description = "Admin can fetch All Bookings of all users and by applying filters(User MetroCardNUmber,  Booking Status)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched all the requested booking"),
      @ApiResponse(responseCode = "401", description = "Not Authorized to access this resource")
  })
  public ResponseEntity<List<BookingDTO>> getAllBookings(@RequestParam(required = false) String metroCardNumber,
      @RequestParam(required = false) Integer bookingStatus);

  @Operation(summary = "Get Booking History of current User", description = "User can fetch All Bookings of user and by applying filters(Booking Status)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched all the requested booking")
  })
  public ResponseEntity<List<BookingDTO>> getAllBookingsOfUser(
      @RequestParam(required = false) Integer bookingStatus,
      HttpServletRequest request);

  @Operation(summary = "Get Booking by Booking Id", description = "User/Admin can fetch booking by id if authorized")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully booked a metro train and Deducted money from User Account"),
      @ApiResponse(responseCode = "400", description = "Not Authorized to access this resource")
  })
  public ResponseEntity<BookingDTO> getById(@Valid @PathVariable String id,
      HttpServletRequest request);
}
