package com.example.application.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import com.example.application.exchanges.userExchanges.UserResponse;
import com.example.application.models.UserEntity;
import com.example.application.repositories.UserRepository;
import com.example.application.services.interfaces.BalanceServices;
import com.example.application.utils.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BalanceServices balanceServices;

  @Autowired
  private JwtUtil jwtUtil;

  private ModelMapper modelMapper = new ModelMapper();

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAll() {
    List<UserEntity> userEntities = userRepository.findAll();

    List<UserResponse> users = userEntities.stream()
        .map(userEntity -> modelMapper.map(userEntity, UserResponse.class))
        .collect(Collectors.toList());

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/{metroCardNumber}")
  public ResponseEntity<UserResponse> getByMetroCardNumber(@PathVariable String metroCardNumber) {
    UserEntity userEntity = userRepository.findByMetroCardNumber(metroCardNumber).orElse(null);

    if (userEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    UserResponse user = modelMapper.map(userEntity, UserResponse.class);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @DeleteMapping("/{metroCardNumber}")
  public ResponseEntity<String> deleteByMetroCardNumber(@PathVariable String metroCardNumber) {
    if (!userRepository.existsByMetroCardNumber(metroCardNumber)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    userRepository.deleteByMetroCardNumber(metroCardNumber);

    return new ResponseEntity<>("Successfully deleted user", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<String> deleteAll() {
    userRepository.deleteAll();

    return new ResponseEntity<>("Successfully deleted all user", HttpStatus.OK);
  }
}
