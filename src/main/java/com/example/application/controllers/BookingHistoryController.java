package com.example.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.models.Booking;
import com.example.application.services.interfaces.BookingServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/booking-history")
public class BookingHistoryController {
  @Autowired
  private BookingServices bookingServices;


  @GetMapping
  public ResponseEntity<List<Booking>> getAllBookings() {
    List<Booking> allBookings = bookingServices.findAllBookings();

    return new ResponseEntity<>(allBookings, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Booking>> getAllUserBookings(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");
    List<Booking> allBookings = bookingServices.findAllUserBookings(metroCardNumber);

    return new ResponseEntity<>(allBookings, HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Booking> getById(@PathVariable String id,
      HttpServletRequest request) {
    try {
      HttpSession session = request.getSession();
      String metroCardNumber = (String) session.getAttribute("metroCardNumber");

      Booking booking = bookingServices.findById(metroCardNumber, id);

      return new ResponseEntity<>(booking, HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

}
