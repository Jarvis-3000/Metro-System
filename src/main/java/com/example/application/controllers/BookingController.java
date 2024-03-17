package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.dtos.bookingDTO.BookingRequest;
import com.example.application.apis.BookingApi;
import com.example.application.dtos.bookingDTO.BookingDTO;
import com.example.application.models.Booking;
import com.example.application.services.interfaces.BookingServices;
import com.example.application.utils.Mappers.BookingDTOMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookingController implements BookingApi{
  @Autowired
  private BookingServices bookingServices;

  @PostMapping
  public ResponseEntity<?> book(@Valid @RequestBody BookingRequest bookingRequest,
      HttpServletRequest request) {

    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    Booking booking = bookingServices.book(metroCardNumber, bookingRequest);

    BookingDTO bookingDTO = BookingDTOMapper.map(booking);

    return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
  }

  @PutMapping("/cancel/{id}")
  public ResponseEntity<?> cancelBooking(@Valid @PathVariable String id,
      HttpServletRequest request) {

    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    Booking booking = bookingServices.cancelById(metroCardNumber, id);

    BookingDTO bookingDTO = BookingDTOMapper.map(booking);

    return new ResponseEntity<>(bookingDTO, HttpStatus.OK);

  }
}
