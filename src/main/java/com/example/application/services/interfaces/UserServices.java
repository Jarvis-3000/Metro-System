package com.example.application.services.interfaces;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.example.application.dtos.authDTO.RegisterUserRequest;
import com.example.application.models.UserEntity;

public interface UserServices {
  public UserEntity register(RegisterUserRequest registerUserRequest);

  public UserEntity save(UserEntity userEntity);

  public List<UserEntity> findAll();

  public UserEntity findByMetroCardNumber(String metroCardNumber) throws ResponseStatusException;

  public boolean existsByMetroCardNumber(String metroCardNumber);

  public void deleteByMetroCardNumber(String metroCardNumber) throws ResponseStatusException;

  public void deleteAll();
}
