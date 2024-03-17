package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.StationApi;
import com.example.application.dtos.stationDTO.AddStationRequest;
import com.example.application.models.Station;
import com.example.application.services.interfaces.StationServices;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/stations")
@Validated // Ensure method-level validation is enabled
public class StationController implements StationApi{
  @Autowired
  private StationServices stationServices;

  @PostMapping
  public ResponseEntity<Station> add(@Valid @RequestBody AddStationRequest addStationRequest) {
    Station savedStation = stationServices.add(addStationRequest);
    return new ResponseEntity<>(savedStation, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Station>> getAllStations() {
    List<Station> stations = stationServices.findAll();

    return new ResponseEntity<>(stations, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Station> getById(@Valid @PathVariable String id) {
    Station station = stationServices.findById(id);
    return new ResponseEntity<>(station, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeById(@Valid @PathVariable String id) {
    stationServices.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> removeAll() {
    stationServices.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);

  }
}
