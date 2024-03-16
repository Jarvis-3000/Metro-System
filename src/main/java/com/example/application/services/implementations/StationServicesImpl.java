package com.example.application.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.dtos.stationDTO.AddStationRequest;
import com.example.application.models.Station;
import com.example.application.repositories.StationRepository;
import com.example.application.services.interfaces.StationServices;

@Service
public class StationServicesImpl implements StationServices {
  @Autowired
  private StationRepository stationRepository;

  @Override
  public Station add(AddStationRequest addStationRequest) throws ResponseStatusException {
    if (stationRepository.existsByName(addStationRequest.getName())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Station already exists with this name");
    }

    Station station = new Station(null, addStationRequest.getName());
    Station savedStation = stationRepository.save(station);

    return savedStation;
  }

  @Override
  public List<Station> findAll() {
    return stationRepository.findAll();
  }

  @Override
  public boolean existsById(String id) {
    return stationRepository.existsById(id);
  }

  @Override
  public Station findById(String id) throws ResponseStatusException {
    return stationRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found by provided id"));
  }

  @Override
  public List<Station> findByName(String name) {
    return stationRepository.findByName(name);
  }

  @Override
  public void deleteById(String id) throws ResponseStatusException {
    if (!stationRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found by provided id");
    }

    stationRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    stationRepository.deleteAll();
  }

}
