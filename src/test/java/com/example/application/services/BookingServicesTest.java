package com.example.application.services;

import java.util.List;

import com.example.application.dtos.bookingDTO.BookingRequest;
import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;
import com.example.application.models.Station;
import com.example.application.models.UserEntity;
import com.example.application.repositories.BookingRepository;
import com.example.application.services.implementations.BalanceServicesImpl;
import com.example.application.services.implementations.BookingServicesImpl;
import com.example.application.services.implementations.FareCalculatorImpl;
import com.example.application.services.implementations.StationServicesImpl;
import com.example.application.services.implementations.UserServicesImpl;
import com.example.application.services.interfaces.BalanceServices;
import com.example.application.services.interfaces.StationServices;
import com.example.application.services.interfaces.UserServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("BookingServicesImpl Tests")
public class BookingServicesTest {
  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private StationServicesImpl stationServices;

  @Mock
  private UserServicesImpl userServices;

  @Mock
  private BalanceServicesImpl balanceServices;

  @Mock
  private FareCalculatorImpl fareCalculator;

  @InjectMocks
  private BookingServicesImpl bookingServices;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test successful booking")
  public void testBook_Success() {
    // Arrange
    String metroCardNumber = "1234567890";
    String originStationId = "originStationId";
    String destinationStationId = "destinationStationId";
    LocalDateTime dateTime = LocalDateTime.now();
    double fare = 10.0;

    BookingRequest bookingRequest = new BookingRequest(originStationId, destinationStationId, dateTime.toString());
    Station originStation = new Station(originStationId, "Origin Station");
    Station destinationStation = new Station(destinationStationId, "Destination Station");

    UserEntity userEntity = new UserEntity();
    userEntity.setBalance(20.0);
    userEntity.setBookings(new ArrayList<>());

    Booking savedBooking = new Booking();
    savedBooking.setBookingStatus(BookingStatus.CONFIRMED);

    when(stationServices.findById(originStationId)).thenReturn(originStation);
    when(stationServices.findById(destinationStationId)).thenReturn(destinationStation);
    when(fareCalculator.calculate(originStationId, destinationStationId)).thenReturn(50.0);
    when(userServices.findByMetroCardNumber(metroCardNumber)).thenReturn(userEntity);
    when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

    // Act
    Booking booking = bookingServices.book(metroCardNumber, bookingRequest);

    // Assert
    assertNotNull(booking);
    assertEquals(BookingStatus.CONFIRMED, booking.getBookingStatus());

    // verify
    verify(stationServices, times(1)).findById(originStationId);
    verify(stationServices, times(1)).findById(destinationStationId);
    verify(userServices, times(1)).findByMetroCardNumber(metroCardNumber);
    verify(bookingRepository, times(1)).save(any(Booking.class));
  }

  @Test
  @DisplayName("Cancel existing booking successfully")
  public void testCancelExistingBooking() {
    // Arrange
    String metroCardNumber = "user123";
    String bookingId = "booking123";

    Booking booking = new Booking();
    booking.setId(bookingId);
    booking.setBookingStatus(BookingStatus.CONFIRMED);

    List<Booking> bookings = new ArrayList<>();
    bookings.add(booking);

    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);
    userEntity.setBookings(new ArrayList<>());
    booking.setUser(userEntity);

    when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
    when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
    when(userServices.save(any(UserEntity.class))).thenReturn(userEntity);

    // Act
    Booking canceledBooking = bookingServices.cancelById(metroCardNumber, bookingId);

    // Assert
    assertNotNull(canceledBooking);
    assertEquals(BookingStatus.CANCELLED, canceledBooking.getBookingStatus());
    verify(bookingRepository, times(1)).save(any(Booking.class));
  }

  @Test
  @DisplayName("Attempt to cancel a booking that is already cancelled")
  public void testCancelAlreadyCancelledBooking() {
    // Arrange
    String metroCardNumber = "user123";
    String bookingId = "booking123";

    Booking booking = new Booking();
    booking.setId(bookingId);
    booking.setBookingStatus(BookingStatus.CANCELLED);

    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);
    booking.setUser(userEntity);

    when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

    // Act and Assert
    assertThrows(ResponseStatusException.class, () -> {
      bookingServices.cancelById(metroCardNumber, bookingId);
    }, "Expected ResponseStatusException");

    verify(bookingRepository, never()).save(any(Booking.class));
  }
}
