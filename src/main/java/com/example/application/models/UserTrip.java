package com.example.application.models;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTrip {
  @Id
  private String id;
  
  @NotNull
  private UserEntity user;
  @NotNull
  private LocalDate date;
  @NotNull
  private Station fromStation;
  @NotNull
  private Station toStation;

  private double fare;
}
