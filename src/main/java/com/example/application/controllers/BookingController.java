package com.example.application.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.exceptions.InsufficientBalanceException;
import com.example.application.exchanges.bookingExchanges.BookingRequest;
import com.example.application.exchanges.bookingExchanges.BookingResponse;
import com.example.application.models.Booking;
import com.example.application.services.interfaces.BookingServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/book")
public class BookingController {
  @Autowired
  private BookingServices bookingServices;

  private ModelMapper modelMapper = new ModelMapper();

  @PostMapping
  public ResponseEntity<?> book(@RequestBody BookingRequest bookingRequest,
      HttpServletRequest request) {
    try {
      HttpSession session = request.getSession();
      String metroCardNumber = (String) session.getAttribute("metroCardNumber");

      Booking booking = bookingServices.book(metroCardNumber, bookingRequest);

      // BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);

      return new ResponseEntity<>(booking, HttpStatus.CREATED);
    } catch (InsufficientBalanceException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @PutMapping("/cancel/{id}")
  public ResponseEntity<?> cancelBooking(@PathVariable String id,
      HttpServletRequest request) {
    try {
      HttpSession session = request.getSession();
      String metroCardNumber = (String) session.getAttribute("metroCardNumber");

      Booking booking = bookingServices.cancelById(metroCardNumber, id);

      // BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);

      return new ResponseEntity<>(booking, HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }
}
