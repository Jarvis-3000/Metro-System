package com.example.application.services.interfaces;

import org.springframework.web.server.ResponseStatusException;

import com.example.application.dtos.bookingDTO.BookingRequest;
import com.example.application.models.Booking;

public interface BookingServices {
  public Booking book(String metroCardNumber, BookingRequest bookingRequest)
      throws ResponseStatusException;

  public Booking cancelById(String metroCardNumber, String id) throws ResponseStatusException;
}
