package com.example.application.exchanges;

import java.time.LocalDateTime;
import java.util.List;

import com.example.application.enums.Role;

import lombok.Data;

@Data
public class UserResponse {
  private String metroCardNumber;
  private double balance;
  private String holderName;
  private LocalDateTime createdAt;
  private List<Role> roles;
}
