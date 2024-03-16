package com.example.application.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.application.dtos.authDTO.LoginRequest;
import com.example.application.dtos.authDTO.LoginResponse;
import com.example.application.dtos.authDTO.RegisterUserRequest;
import com.example.application.dtos.userDTO.UserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "User Authentication", description = "User Authentication Creation and Login")
public interface AuthApi {
  public ResponseEntity<UserDTO> register(@RequestBody RegisterUserRequest registerUserRequest);

  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request);
}
