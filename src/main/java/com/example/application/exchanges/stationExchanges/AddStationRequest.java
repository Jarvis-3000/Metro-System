package com.example.application.exchanges.stationExchanges;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddStationRequest {
  @NotBlank
  private String name;
}
