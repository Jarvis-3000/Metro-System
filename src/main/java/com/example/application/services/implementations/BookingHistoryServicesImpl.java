package com.example.application.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;
import com.example.application.repositories.BookingRepository;
import com.example.application.services.interfaces.BookingHistoryServices;
import com.example.application.services.interfaces.UserServices;

@Service
public class BookingHistoryServicesImpl implements BookingHistoryServices {
  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserServices userServices;

  @Override
  public List<Booking> findAllBookings() {
    return bookingRepository.findAll();
  }

  @Override
  public List<Booking> findAllBookingsByBookingStatus(BookingStatus bookingStatus) {
    return bookingRepository.findByBookingStatus(bookingStatus);
  }

  @Override
  public List<Booking> findAllUserBookings(String metroCardNumber) {
    if (!userServices.existsByMetroCardNumber(metroCardNumber)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "User by metroCardNumber " + metroCardNumber + " does not exist");
    }

    return bookingRepository.findByUserMetroCardNumber(metroCardNumber);
  }

  @Override
  public List<Booking> findByMetroCardNumberAndBookingStatus(String metroCardNumber, BookingStatus bookingStatus) {
    if (!userServices.existsByMetroCardNumber(metroCardNumber)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "User by metroCardNumber " + metroCardNumber + " does not exist");
    }

    List<Booking> filteredBookings = bookingRepository
        .findByUserMetroCardNumberAndBookingStatus(metroCardNumber, bookingStatus);

    return filteredBookings;
  }

  @Override
  public Booking findById(String metroCardNumber, String id) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // booking id must be associated with the current logged in user
    if (booking.getUser().getMetroCardNumber() != metroCardNumber) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return booking;
  }
}
