package com.example.application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import com.example.application.models.Booking;

public interface BookingRepository extends MongoRepository<Booking, String>{
  public List<Booking> findByUserMetroCardNumber(String userMetroCardNumber);
}
