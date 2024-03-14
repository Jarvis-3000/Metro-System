package com.example.application.exchanges.bookingExchanges;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
  @NotBlank
  private String originStationId;
  @NotBlank
  private String destinationStationId;
  @NotNull 
  private LocalDateTime dateTime;
}
