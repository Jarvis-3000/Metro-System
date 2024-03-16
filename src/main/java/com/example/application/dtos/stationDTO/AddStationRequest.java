package com.example.application.dtos.stationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddStationRequest {
  @NotBlank(message = "Station name can not be blank")
  private String name;
}
