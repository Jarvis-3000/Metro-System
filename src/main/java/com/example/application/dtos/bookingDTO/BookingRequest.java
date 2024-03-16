package com.example.application.dtos.bookingDTO;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
  @NotBlank(message = "Origin station id is required")
  private String originStationId;
  @NotBlank(message = "Destination station id is required")
  private String destinationStationId;
  
  @NotNull(message = "Date Time is required")
  @FutureOrPresent
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime dateTime;
}
