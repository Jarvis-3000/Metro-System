package com.example.application.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.application.exchanges.UserResponse;
import com.example.application.exchanges.authExchanges.LoginRequest;
import com.example.application.exchanges.authExchanges.LoginResponse;
import com.example.application.exchanges.authExchanges.RegisterUserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Authentication", description = "User Authentication Creation and Login")
public interface AuthApi {
  public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest registerUserRequest);

  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest);
}
