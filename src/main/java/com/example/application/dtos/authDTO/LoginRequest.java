package com.example.application.dtos.authDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String metroCardNumber;
  @NotBlank
  private String password;
}
