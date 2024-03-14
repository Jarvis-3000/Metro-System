package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.services.interfaces.FareCalculator;

@RestController
@RequestMapping("/fare")
public class FareController {
  @Autowired
  private FareCalculator fareCalculator;

  @GetMapping
  public ResponseEntity<Double> getFare(@RequestParam String originStationId,
      @RequestParam String destinationStationId) {
    try {
      Double fare = fareCalculator.calculate(originStationId,
          destinationStationId);
      return new ResponseEntity<>(fare, HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }
}
