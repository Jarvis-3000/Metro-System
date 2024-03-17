package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.ProfileApi;
import com.example.application.dtos.userDTO.UserDTO;
import com.example.application.models.UserEntity;
import com.example.application.services.interfaces.UserServices;
import com.example.application.utils.Mappers.UserDTOMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/profile")
public class ProfileController implements ProfileApi{
  @Autowired
  private UserServices userServices;

  @GetMapping
  public ResponseEntity<UserDTO> getProfile(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    UserEntity userEntity = userServices.findByMetroCardNumber(metroCardNumber);

    UserDTO userDTO = UserDTOMapper.map(userEntity);

    return new ResponseEntity<>(userDTO, HttpStatus.OK);
  }
}
