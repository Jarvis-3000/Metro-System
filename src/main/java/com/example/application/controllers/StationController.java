package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.exchanges.stationExchanges.AddStationRequest;
import com.example.application.models.Station;
import com.example.application.services.interfaces.StationServices;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
  @Autowired
  private StationServices stationServices;

  @PostMapping
  public ResponseEntity<Station> add(@RequestBody AddStationRequest addStationRequest) {
    try {
      Station savedStation = stationServices.add(addStationRequest);
      return new ResponseEntity<>(savedStation, HttpStatus.CREATED);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @GetMapping
  public ResponseEntity<List<Station>> getAllStations() {
    List<Station> stations = stationServices.findAll();

    return new ResponseEntity<>(stations, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Station> getById(@PathVariable String id) {
    try {
      Station station = stationServices.findById(id);
      return new ResponseEntity<>(station, HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeById(@PathVariable String id) {
    try {
      stationServices.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }

  @DeleteMapping
  public ResponseEntity<?> removeAll() {
    try {
      stationServices.deleteAll();
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getStatusCode());
    }
  }
}
