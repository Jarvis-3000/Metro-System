package com.example.application.exchanges.authExchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
  private String message;
  private String token;
}
