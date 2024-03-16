package com.example.application.services.interfaces;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;

public interface BookingHistoryServices {
  // returns all bookings including every user and every bookingStatus
  public List<Booking> findAllBookings();

  // returns all bookings filtered by bookingStatus
  public List<Booking> findAllBookingsByBookingStatus(BookingStatus bookingStatus);

  // returns all bookings of a user without any filter
  public List<Booking> findAllUserBookings(String metroCardNumber);

  // returns all bookings of a user filtered by bookingStatus
  public List<Booking> findByMetroCardNumberAndBookingStatus(String metroCardNumber, BookingStatus bookingStatus);

  public Booking findById(String metroCardNumber, String id) throws ResponseStatusException;
}
