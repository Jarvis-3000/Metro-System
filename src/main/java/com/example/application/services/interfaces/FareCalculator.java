package com.example.application.services.interfaces;

import org.springframework.web.server.ResponseStatusException;

public interface FareCalculator {
  public double calculate(String originStationId, String destinationStationId) throws ResponseStatusException;
}
