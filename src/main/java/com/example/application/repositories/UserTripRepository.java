package com.example.application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.application.models.UserTrip;

public interface UserTripRepository extends MongoRepository<UserTrip, String>{
  
}
