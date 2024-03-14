package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.models.UserEntity;
import com.example.application.repositories.UserRepository;
import com.example.application.services.interfaces.BalanceServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/balance")
public class BalanceController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BalanceServices balanceServices;


  @PutMapping
  public ResponseEntity<String> add(HttpServletRequest request,
      @RequestParam Double money) {
    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    UserEntity user = userRepository.findByMetroCardNumber(metroCardNumber).get();
    balanceServices.addMoney(user, money);

    userRepository.save(user);

    return new ResponseEntity<>("Successfully balance recharged", HttpStatus.ACCEPTED);
  }
}
