package com.example.application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.application.models.Station;

public interface StationRepository extends MongoRepository<Station, String>{
  
}
