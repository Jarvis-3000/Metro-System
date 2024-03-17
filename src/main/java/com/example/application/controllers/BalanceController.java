package com.example.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.apis.BalanceApi;
import com.example.application.models.UserEntity;
import com.example.application.services.interfaces.BalanceServices;
import com.example.application.services.interfaces.UserServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/balance")
public class BalanceController implements BalanceApi{
  @Autowired
  private UserServices userServices;

  @Autowired
  private BalanceServices balanceServices;

  @PutMapping
  public ResponseEntity<String> addBalance(HttpServletRequest request,
      @Valid @Min(value = 0, message = "Amount must be positive") @RequestParam(required = true) Double money) {

    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    UserEntity user = userServices.findByMetroCardNumber(metroCardNumber);
    balanceServices.addMoney(user, money);

    userServices.save(user);

    return new ResponseEntity<>("Successfully balance recharged", HttpStatus.ACCEPTED);
  }

  @GetMapping
  public ResponseEntity<Double> getBalance(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String metroCardNumber = (String) session.getAttribute("metroCardNumber");

    UserEntity user = userServices.findByMetroCardNumber(metroCardNumber);

    return new ResponseEntity<>(user.getBalance(), HttpStatus.OK);
  }
}
