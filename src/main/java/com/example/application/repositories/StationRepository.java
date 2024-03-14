package com.example.application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import com.example.application.models.Station;

public interface StationRepository extends MongoRepository<Station, String>{
  public boolean existsByName(String name);
  public List<Station> findByName(String name);
}
