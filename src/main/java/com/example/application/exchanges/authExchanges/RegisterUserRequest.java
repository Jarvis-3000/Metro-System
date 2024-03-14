package com.example.application.exchanges.authExchanges;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserRequest {
  @NotBlank
  private String holderName;
  @NotBlank
  private String password;

  private boolean admin;
}
