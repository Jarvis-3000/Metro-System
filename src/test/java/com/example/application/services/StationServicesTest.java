package com.example.application.services;

import com.example.application.dtos.stationDTO.AddStationRequest;
import com.example.application.models.Station;
import com.example.application.repositories.StationRepository;
import com.example.application.services.implementations.StationServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StationServicesImpl Tests")
public class StationServicesTest {

  @Mock
  private StationRepository stationRepository;

  @InjectMocks
  private StationServicesImpl stationServices;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test add station")
  public void testAddStation() {
    // Arrange
    AddStationRequest addStationRequest = new AddStationRequest("StationName");
    Station station = new Station(null, addStationRequest.getName());

    // Mock stationRepository save method
    when(stationRepository.existsByName(addStationRequest.getName())).thenReturn(false);
    when(stationRepository.save(station)).thenReturn(station);

    // Act
    Station savedStation = stationServices.add(addStationRequest);

    // Assert
    assertNotNull(savedStation);
    assertEquals(addStationRequest.getName(), savedStation.getName());
  }

  @Test
  @DisplayName("Test add station with existing name")
  public void testAddStationWithExistingName() {
    // Arrange
    AddStationRequest addStationRequest = new AddStationRequest("StationName");

    // Mock stationRepository existsByName method
    when(stationRepository.existsByName(addStationRequest.getName())).thenReturn(true);

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      stationServices.add(addStationRequest);
    });
  }

  @Test
  @DisplayName("Test find all stations")
  public void testFindAllStations() {
    // Arrange
    List<Station> expectedStations = new ArrayList<>();
    expectedStations.add(new Station("1", "Station1"));
    expectedStations.add(new Station("2", "Station2"));

    // Mock stationRepository findAll method
    when(stationRepository.findAll()).thenReturn(expectedStations);

    // Act
    List<Station> actualStations = stationServices.findAll();

    // Assert
    assertEquals(expectedStations.size(), actualStations.size());
    assertEquals(expectedStations, actualStations);
  }

  @Test
  @DisplayName("Test find station by id")
  public void testFindStationById() {
    // Arrange
    String stationId = "1";
    Station expectedStation = new Station(stationId, "StationName");

    // Mock stationRepository findById method
    when(stationRepository.findById(stationId)).thenReturn(Optional.of(expectedStation));

    // Act
    Station actualStation = stationServices.findById(stationId);

    // Assert
    assertNotNull(actualStation);
    assertEquals(expectedStation, actualStation);
  }

  @Test
  @DisplayName("Test find station by non-existing id")
  public void testFindStationByNonExistingId() {
    // Arrange
    String stationId = "1";

    // Mock stationRepository findById method
    when(stationRepository.findById(stationId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      stationServices.findById(stationId);
    });
  }

  @Test
  @DisplayName("Test delete station by id")
  public void testDeleteStationById() {
    // Arrange
    String stationId = "1";

    // Mock stationRepository existsById and deleteById methods
    when(stationRepository.existsById(stationId)).thenReturn(true);

    // Act
    stationServices.deleteById(stationId);

    // Assert
    verify(stationRepository, times(1)).deleteById(stationId);
  }

  @Test
  @DisplayName("Test delete station by non-existing id")
  public void testDeleteStationByNonExistingId() {
    // Arrange
    String stationId = "1";

    // Mock stationRepository existsById method
    when(stationRepository.existsById(stationId)).thenReturn(false);

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> {
      stationServices.deleteById(stationId);
    });
  }

}
