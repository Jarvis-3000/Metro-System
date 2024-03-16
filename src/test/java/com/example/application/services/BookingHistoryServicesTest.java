package com.example.application.services;

import com.example.application.enums.BookingStatus;
import com.example.application.models.Booking;
import com.example.application.models.UserEntity;
import com.example.application.repositories.BookingRepository;
import com.example.application.services.implementations.BookingHistoryServicesImpl;
import com.example.application.services.implementations.UserServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("BookingHistoryServicesImpl Tests")
public class BookingHistoryServicesTest {

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private UserServicesImpl userServices;

  @InjectMocks
  private BookingHistoryServicesImpl bookingHistoryServices;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test findAllBookings")
  public void testFindAllBookings() {
    // Arrange
    List<Booking> bookings = new ArrayList<>();
    when(bookingRepository.findAll()).thenReturn(bookings);

    // Act
    List<Booking> result = bookingHistoryServices.findAllBookings();

    // Assert
    assertEquals(bookings, result);
  }

  @Test
  @DisplayName("Test findAllBookingsByBookingStatus")
  public void testFindAllBookingsByBookingStatus() {
    // Arrange
    BookingStatus bookingStatus = BookingStatus.CONFIRMED;
    List<Booking> bookings = new ArrayList<>();
    when(bookingRepository.findByBookingStatus(bookingStatus)).thenReturn(bookings);

    // Act
    List<Booking> result = bookingHistoryServices.findAllBookingsByBookingStatus(bookingStatus);

    // Assert
    assertEquals(bookings, result);
  }

  @Test
  @DisplayName("Test findAllUserBookings")
  public void testFindAllUserBookings() {
    // Arrange
    String metroCardNumber = "1234567890";
    List<Booking> bookings = new ArrayList<>();
    when(userServices.existsByMetroCardNumber(metroCardNumber)).thenReturn(true);
    when(bookingRepository.findByUserMetroCardNumber(metroCardNumber)).thenReturn(bookings);

    // Act
    List<Booking> result = bookingHistoryServices.findAllUserBookings(metroCardNumber);

    // Assert
    assertEquals(bookings, result);
  }

  @Test
  @DisplayName("Test findByMetroCardNumberAndBookingStatus")
  public void testFindByMetroCardNumberAndBookingStatus() {
    // Arrange
    String metroCardNumber = "1234567890";
    BookingStatus bookingStatus = BookingStatus.CONFIRMED;
    List<Booking> bookings = new ArrayList<>();

    when(userServices.existsByMetroCardNumber(metroCardNumber)).thenReturn(true);
    when(bookingRepository.findByUserMetroCardNumberAndBookingStatus(metroCardNumber, bookingStatus))
        .thenReturn(bookings);

    // Act
    List<Booking> result = bookingHistoryServices.findByMetroCardNumberAndBookingStatus(metroCardNumber, bookingStatus);

    // Assert
    assertEquals(bookings, result);
  }

  @Test
  @DisplayName("Test findById")
  public void testFindById() {
    // Arrange
    String metroCardNumber = "1234567890";
    String id = "bookingId";

    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);

    Booking booking = new Booking();
    booking.setId(id);
    booking.setUser(userEntity);

    when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

    // Act
    Booking result = bookingHistoryServices.findById(metroCardNumber, id);

    // Assert
    assertNotNull(result);
    assertEquals(id, result.getId());
  }

  @Test
  @DisplayName("Test findById with NotFound")
  public void testFindByIdNotFound() {
    // Arrange
    String metroCardNumber = "1234567890";
    String id = "bookingId";
    when(bookingRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      bookingHistoryServices.findById(metroCardNumber, id);
    });
  }

  @Test
  @DisplayName("Test findById with Forbidden")
  public void testFindByIdForbidden() {
    // Arrange
    String metroCardNumber = "1234567890";
    String id = "bookingId";
    Booking booking = new Booking();
    booking.setId(id);
    booking.setUser(new UserEntity());
    when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      bookingHistoryServices.findById("otherMetroCardNumber", id);
    });
  }
}
