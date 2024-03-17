package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.apis.FareApi;
import com.example.application.services.interfaces.FareCalculator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fare")
public class FareController implements FareApi{
  @Autowired
  private FareCalculator fareCalculator;

  @GetMapping
  public ResponseEntity<Double> getFare(
      @Valid @RequestParam(required = true) String originStationId,
      @Valid @RequestParam(required = true) String destinationStationId) {
    Double fare = fareCalculator.calculate(originStationId, destinationStationId);
    return new ResponseEntity<>(fare, HttpStatus.OK);
  }
}
