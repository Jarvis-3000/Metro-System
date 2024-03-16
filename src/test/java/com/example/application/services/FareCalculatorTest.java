package com.example.application.services;

import com.example.application.services.implementations.FareCalculatorImpl;
import com.example.application.services.implementations.StationServicesImpl;
import com.example.application.services.interfaces.StationServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("FareCalculatorImpl Tests")
public class FareCalculatorTest {

  @Mock
  private StationServicesImpl stationServices;

  @InjectMocks
  private FareCalculatorImpl fareCalculator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test calculate fare")
  public void testCalculateFare() {
    // Arrange
    String originStationId = "origin";
    String destinationStationId = "destination";
    double expectedFare = 50; // Fare rate is 5 Rs and distance is 10

    // Mock the existence of stations
    when(stationServices.existsById(originStationId)).thenReturn(true);
    when(stationServices.existsById(destinationStationId)).thenReturn(true);

    // Act
    double actualFare = fareCalculator.calculate(originStationId, destinationStationId);

    // Assert
    assertEquals(expectedFare, actualFare);
  }

  @Test
  @DisplayName("Test calculate fare with station not found")
  public void testCalculateFareWithStationNotFound() {
    // Arrange
    String originStationId = "origin";
    String destinationStationId = "destination";

    // Mock one of the stations not found
    when(stationServices.existsById(originStationId)).thenReturn(true);
    when(stationServices.existsById(destinationStationId)).thenReturn(false);

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      fareCalculator.calculate(originStationId, destinationStationId);
    });
  }
}
