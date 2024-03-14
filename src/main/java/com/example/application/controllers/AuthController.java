package com.example.application.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.AuthApi;
import com.example.application.enums.Role;
import com.example.application.exchanges.authExchanges.LoginRequest;
import com.example.application.exchanges.authExchanges.LoginResponse;
import com.example.application.exchanges.authExchanges.RegisterUserRequest;
import com.example.application.exchanges.userExchanges.UserResponse;
import com.example.application.models.UserEntity;
import com.example.application.repositories.UserRepository;
import com.example.application.utils.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  private ModelMapper modelMapper = new ModelMapper();

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest registerRequest) {
    UserEntity userEntity = new UserEntity();
    LocalDateTime dateTime = LocalDateTime.now();

    userEntity.setHolderName(registerRequest.getHolderName());
    userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    if (registerRequest.isAdmin()) {
      userEntity.setRoles(Collections.singleton(Role.ADMIN));
    } else {
      userEntity.setRoles(Collections.singleton(Role.USER));
    }

    userEntity.setCreatedAt(dateTime);
    userEntity.setBalance(1000);  //joining initial money
    userEntity.setBookings(new ArrayList<>());

    UserEntity savedUser = userRepository.save(userEntity);
    UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);

    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getMetroCardNumber(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String message = "User login successfull";
    String token = jwtUtil.generateToken(authentication);

    return new ResponseEntity<>(new LoginResponse(message, token), HttpStatus.CREATED);
  }
}
