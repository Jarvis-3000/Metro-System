package com.example.application.services.implementations;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.enums.BookingStatus;
import com.example.application.exceptions.InsufficientBalanceException;
import com.example.application.exchanges.bookingExchanges.BookingRequest;
import com.example.application.models.Booking;
import com.example.application.models.Station;
import com.example.application.models.UserEntity;
import com.example.application.repositories.BookingRepository;
import com.example.application.repositories.UserRepository;
import com.example.application.services.interfaces.BalanceServices;
import com.example.application.services.interfaces.BookingServices;
import com.example.application.services.interfaces.FareCalculator;
import com.example.application.services.interfaces.StationServices;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BookingServicesImpl implements BookingServices {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private StationServices stationServices;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private FareCalculator fareCalculator;

  @Autowired
  private BalanceServices balanceServices;

  @Override
  public List<Booking> findAllBookings() {
    return bookingRepository.findAll();
  }

  @Override
  public List<Booking> findAllUserBookings(String metroCardNumber) {
    return bookingRepository.findByUserMetroCardNumber(metroCardNumber);
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

  @Override
  // @Transactional
  // Transaction numbers are only allowed on a replica set member or mongos
  public Booking book(String metroCardNumber, BookingRequest bookingRequest)
      throws ResponseStatusException {
    try {
      // extract the values from bookingRequest
      String originStationId = bookingRequest.getOriginStationId();
      String destinationStationId = bookingRequest.getDestinationStationId();
      LocalDateTime dateTime = bookingRequest.getDateTime();

      // fetch the entities from database through services
      Station originStation = stationServices.findById(originStationId);
      Station destinationStation = stationServices.findById(destinationStationId);
      UserEntity userEntity = userRepository.findByMetroCardNumber(metroCardNumber).get();

      // calculate fare
      double fare = fareCalculator.calculate(originStationId, destinationStationId);

      // deduct the fare from user entity
      balanceServices.deductFare(userEntity, fare);

      // create a booking entity
      Booking booking = new Booking(null, userEntity, originStation, destinationStation, dateTime, fare,
          BookingStatus.CONFIRMED);

      // save in booking repository
      Booking savedBooking = bookingRepository.save(booking);

      // save the booking in the associated user repository
      userEntity.getBookings().add(savedBooking);
      userRepository.save(userEntity);

      return savedBooking;
    } catch (ResponseStatusException e) {
      throw e;
    } catch (InsufficientBalanceException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  // @Transactional
  public Booking cancelById(String metroCardNumber, String id) throws ResponseStatusException {
    try {
      Booking booking = bookingRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      // booking id must be associated with the current logged in user
      if (!booking.getUser().getMetroCardNumber().equals(metroCardNumber)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }

      // update the booking status to cancelled
      booking.setBookingStatus(BookingStatus.CANCELLED);
      Booking updatedBooking = bookingRepository.save(booking);

      UserEntity updatedUser = booking.getUser();

      // update/replace the booking into user
      List<Booking> updatedBookings = updatedUser.getBookings().stream().map((userBooking) -> {
        if (userBooking.getId().equals(id)) {
          return updatedBooking;
        }
        return userBooking;
      }).collect(Collectors.toList());

      updatedUser.setBookings(updatedBookings);

      // refund money
      updatedUser.recharge(booking.getFare());

      // update the user in repository with updated boking
      userRepository.save(updatedUser);

      return updatedBooking;
    } catch (ResponseStatusException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
