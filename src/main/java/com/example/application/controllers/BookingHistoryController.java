package com.example.application.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.BookingHistoryApi;
import com.example.application.dtos.bookingDTO.BookingDTO;
import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;
import com.example.application.services.interfaces.BookingHistoryServices;
import com.example.application.utils.Mappers.BookingDTOMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking-history")
public class BookingHistoryController implements BookingHistoryApi {
  @Autowired
  private BookingHistoryServices bookingHistoryServices;

  @GetMapping("/all")
  public ResponseEntity<List<BookingDTO>> getAllBookings(@RequestParam(required = false) String metroCardNumber,
      @RequestParam(required = false) Integer bookingStatus) {
    List<Booking> bookings;
    BookingStatus status = null;

    if (bookingStatus != null) {
      status = bookingStatus.toString().equals("1") ? BookingStatus.CONFIRMED
          : BookingStatus.CANCELLED;
    }

    if (metroCardNumber != null && bookingStatus != null) {
      bookings = bookingHistoryServices.findByMetroCardNumberAndBookingStatus(metroCardNumber, status);
    } else if (metroCardNumber != null) {
      bookings = bookingHistoryServices.findAllUserBookings(metroCardNumber);
    } else if (bookingStatus != null) {
      bookings = bookingHistoryServices.findAllBookingsByBookingStatus(status);
    } else {
      bookings = bookingHistoryServices.findAllBookings();
    }

    List<BookingDTO> allBookingDTOs = bookings
        .stream()
        .map((booking) -> BookingDTOMapper.map(booking))
        .collect(Collectors.toList());

    return new ResponseEntity<>(allBookingDTOs, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<BookingDTO>> getAllBookingsOfUser(
      @RequestParam(required = false) Integer bookingStatus,
      HttpServletRequest request) {

    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    List<Booking> bookings;
    BookingStatus status = null;

    if (bookingStatus != null) {
      status = bookingStatus.toString().equals("1") ? BookingStatus.CONFIRMED
          : BookingStatus.CANCELLED;
    }

    if (bookingStatus != null) {
      bookings = bookingHistoryServices.findByMetroCardNumberAndBookingStatus(metroCardNumber, status);
    } else {
      bookings = bookingHistoryServices.findAllUserBookings(metroCardNumber);
    }

    List<BookingDTO> filteredBookingDTOs = bookings
        .stream()
        .map((booking) -> BookingDTOMapper.map(booking))
        .collect(Collectors.toList());

    return new ResponseEntity<>(filteredBookingDTOs, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookingDTO> getById(@Valid @PathVariable String id,
      HttpServletRequest request) {
    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");
    boolean isAdmin = (boolean) session.getAttribute("isAdmin");

    Booking booking = bookingHistoryServices.findById(metroCardNumber, id);

    // check for authorizations
    if (!isAdmin) {
      if (!booking.getUser().getMetroCardNumber().equals(metroCardNumber)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }
    }

    BookingDTO bookingDTO = BookingDTOMapper.map(booking);

    return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
  }
}
