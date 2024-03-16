package com.example.application.dtos.userDTO;

import java.time.LocalDateTime;
import java.util.List;

import com.example.application.dtos.bookingDTO.BookingBasicDTO;
import com.example.application.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String metroCardNumber;
  private String holderName;
  private double balance;
  private LocalDateTime createdAt;
  private List<Role> roles;
  private List<BookingBasicDTO> bookings;
}
