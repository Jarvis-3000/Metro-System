package com.example.application.dtos.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicDto {
  private String metroCardNumber;
  private String holderName;
}
