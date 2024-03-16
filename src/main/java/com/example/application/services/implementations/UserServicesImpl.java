package com.example.application.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.application.dtos.authDTO.RegisterUserRequest;
import com.example.application.enums.Role;
import com.example.application.models.UserEntity;
import com.example.application.repositories.UserRepository;
import com.example.application.services.interfaces.UserServices;

@Service
public class UserServicesImpl implements UserServices {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserEntity save(UserEntity userEntity) throws IllegalArgumentException {
    if (userEntity == null) {
      throw new IllegalArgumentException();
    }

    return userRepository.save(userEntity);
  }

  @Override
  public UserEntity register(RegisterUserRequest registerUserRequest) {
    UserEntity userEntity = new UserEntity();
    LocalDateTime dateTime = LocalDateTime.now();

    userEntity.setHolderName(registerUserRequest.getHolderName());
    userEntity.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
    userEntity.setCreatedAt(dateTime);
    userEntity.setBalance(1000); // joining initial money
    userEntity.setBookings(new ArrayList<>());

    // assign roles based on admin condition
    if (registerUserRequest.isAdmin()) {
      userEntity.setRoles(Collections.singleton(Role.ADMIN));
    } else {
      userEntity.setRoles(Collections.singleton(Role.USER));
    }

    UserEntity savedUser = userRepository.save(userEntity);

    return savedUser;
  }

  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }

  @Override
  public UserEntity findByMetroCardNumber(String metroCardNumber) throws ResponseStatusException {
    UserEntity userEntity = userRepository.findByMetroCardNumber(metroCardNumber)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found by provided metro card number"));

    return userEntity;
  }

  @Override
  public boolean existsByMetroCardNumber(String metroCardNumber) {
    return userRepository.existsByMetroCardNumber(metroCardNumber);
  }

  @Override
  public void deleteByMetroCardNumber(String metroCardNumber) throws ResponseStatusException {
    if (!userRepository.existsByMetroCardNumber(metroCardNumber)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found by provided metro card number");
    }

    userRepository.deleteByMetroCardNumber(metroCardNumber);
  }

  @Override
  public void deleteAll() {
    userRepository.deleteAll();
  }
}
