package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.example.application.enums.Role;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id
  private String metroCardNumber;

  @NotBlank
  private String password;
  
  @NotBlank
  private String holderName;

  @Min(0)
  private double balance;

  @NotEmpty
  private Set<Role> roles;

  private LocalDateTime createdAt;
}
