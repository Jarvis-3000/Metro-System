package com.example.application.services.interfaces;

import com.example.application.exchanges.stationExchanges.AddStationRequest;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.example.application.models.Station;

public interface StationServices {
  public Station add(AddStationRequest addStationRequest) throws ResponseStatusException;

  public List<Station> findAll();

  public boolean existsById(String id);

  public Station findById(String id) throws ResponseStatusException;

  public List<Station> findByName(String name) throws ResponseStatusException;

  public void deleteById(String id) throws ResponseStatusException;

  public void deleteAll();
}
