package com.example.application.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingDateTimeUtils {
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  public static LocalDateTime parse(String dateTimeString) {
    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    LocalDateTime now = LocalDateTime.now();

    // past date time is not valid for booking
    if (dateTime.isBefore(now)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking for Past Dates are not allowed");
    }

    return dateTime;
  }
}
