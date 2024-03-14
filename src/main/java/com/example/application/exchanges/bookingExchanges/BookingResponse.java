package com.example.application.exchanges.bookingExchanges;

import java.time.LocalDateTime;

import com.example.application.enums.BookingStatus;
import com.example.application.models.Station;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponse {
  private String id;
  private Station originStation;
  private Station destinationStation;
  private LocalDateTime dateTime;
  private double fare;
  private BookingStatus bookingStatus;
}
