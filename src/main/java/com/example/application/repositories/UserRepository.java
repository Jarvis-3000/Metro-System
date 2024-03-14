package com.example.application.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.application.models.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
  Optional<UserEntity> findByMetroCardNumber(String metroCardNumber);

  boolean existsByMetroCardNumber(String metroCardNumber);

  void deleteByMetroCardNumber(String metroCardNumber);
}
