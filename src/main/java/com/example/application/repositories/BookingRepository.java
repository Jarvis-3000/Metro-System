package com.example.application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
  // find all bookings by booking status, whether Confirmed or Cancelled
  // findAll() + filter
  public List<Booking> findByBookingStatus(BookingStatus bookingStatus);

  // find all bookings of a user by metroCardNUmber
  // findAll() + filter by userMetroCardNumber
  public List<Booking> findByUserMetroCardNumber(String userMetroCardNumber);

  // find all bookings of a user filtered by booking status
  // findByUserMetroCardNumber() + filter bookingStatus
  public List<Booking> findByUserMetroCardNumberAndBookingStatus(String userMetroCardNumber,
      BookingStatus bookingStatus);
}
