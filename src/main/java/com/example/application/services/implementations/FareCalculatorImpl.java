package com.example.application.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.services.interfaces.FareCalculator;
import com.example.application.services.interfaces.StationServices;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FareCalculatorImpl implements FareCalculator {
  private final int FARE_RATE = 5; // 5 rs
  // implement calculate method which can calculate fare based on stations

  @Autowired
  private StationServices stationServices;

  @Override
  public double calculate(String originStationId, String destinationStationId) throws ResponseStatusException {
    if (!stationServices.existsById(originStationId) || !stationServices.existsById(destinationStationId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Station not found by provided station id");
    }

    double fare = 10;

    log.info("Fare from " + originStationId + " to " + destinationStationId + " = " + fare);

    return fare * FARE_RATE;
  }

}
