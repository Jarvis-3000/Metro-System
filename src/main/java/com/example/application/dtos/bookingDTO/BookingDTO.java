package com.example.application.dtos.bookingDTO;

import java.time.LocalDateTime;

import com.example.application.dtos.userDTO.UserBasicDto;
import com.example.application.enums.BookingStatus;
import com.example.application.models.Station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
  private String id;
  private Station originStation;
  private Station destinationStation;
  private LocalDateTime dateTime;
  private BookingStatus bookingStatus;
  private UserBasicDto user;
  private double fare;
}
