package com.example.application.exchanges.authExchanges;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String metroCardNumber;
  @NotBlank
  private String password;
}
