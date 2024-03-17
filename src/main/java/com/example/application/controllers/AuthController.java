package com.example.application.controllers;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.AuthApi;
import com.example.application.config.UserDetailsServiceImpl;
import com.example.application.dtos.authDTO.LoginRequest;
import com.example.application.dtos.authDTO.LoginResponse;
import com.example.application.dtos.authDTO.RegisterUserRequest;
import com.example.application.dtos.userDTO.UserDTO;
import com.example.application.enums.Role;
import com.example.application.models.UserEntity;
import com.example.application.services.interfaces.UserServices;
import com.example.application.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController implements AuthApi {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserServices userServices;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  private ModelMapper modelMapper = new ModelMapper();

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterUserRequest registerRequest) {
    try {
      UserEntity savedUser = userServices.register(registerRequest);

      UserDTO userDTO = modelMapper.map(savedUser, UserDTO.class);

      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    Authentication authentication = authenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getMetroCardNumber(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String message = "User login successfull";
    String token = jwtUtil.generateToken(authentication);

    // Store use details in the session for later usages
    storeUserInSession(request, loginRequest);

    return new ResponseEntity<>(new LoginResponse(message, token), HttpStatus.CREATED);
  }

  private void storeUserInSession(HttpServletRequest request, LoginRequest loginRequest) {
    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getMetroCardNumber());
    HttpSession session = request.getSession();

    session.setAttribute("metroCardNumber", loginRequest.getMetroCardNumber());
    session.setAttribute("isAdmin", includesAdminAuthority(userDetails.getAuthorities()));

    log.info("User session created and stored");
  }

  private boolean includesAdminAuthority(Collection<? extends GrantedAuthority> collection) {
    // Iterate over the collection of roles
    for (GrantedAuthority grantedAuthority : collection) {
      // Check if the role is ADMIN
      if (Role.ADMIN.equals(grantedAuthority.getAuthority())) {
        return true; // Return true if ADMIN role is found
      }
    }
    return false; // Return false if ADMIN role is not found
  }

}
