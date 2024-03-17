package com.example.application.dtos.bookingDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
  @NotBlank(message = "Origin station id is required")
  private String originStationId;
  
  @NotBlank(message = "Destination station id is required")
  private String destinationStationId;
  
  @NotNull(message = "Date Time is required")
  private String dateTime;
}
