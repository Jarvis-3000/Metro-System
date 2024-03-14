package com.example.application.services.interfaces;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import com.example.application.exchanges.bookingExchanges.BookingRequest;
import com.example.application.models.Booking;

public interface BookingServices {
  public List<Booking> findAllBookings();

  public List<Booking> findAllUserBookings(String metroCardNumber);

  public Booking findById(String metroCardNumber, String id) throws ResponseStatusException;

  public Booking book(String metroCardNumber, BookingRequest bookingRequest)
      throws ResponseStatusException;

  public Booking cancelById(String metroCardNumber, String id) throws ResponseStatusException;
}
