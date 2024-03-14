package com.example.application.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.services.interfaces.FareCalculator;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FareCalculatorImpl implements FareCalculator {
  private final int FARE_RATE = 5; // 5 rs
  // implement calculate method which can calculate fare based on stations

  @Override
  public double calculate(String originStationId, String destinationStationId) throws ResponseStatusException {
    double fare = 10;

    log.info("Fare from " + originStationId + " to " + destinationStationId + " = " + fare);

    return fare * FARE_RATE;
  }

}
