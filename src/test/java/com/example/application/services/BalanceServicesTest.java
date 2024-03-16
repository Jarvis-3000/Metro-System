package com.example.application.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.application.exceptions.InsufficientBalanceException;
import com.example.application.models.UserEntity;
import com.example.application.services.implementations.BalanceServicesImpl;
import com.example.application.services.interfaces.BalanceServices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Balance Services Impl Tests")
public class BalanceServicesTest {
  private BalanceServices balanceServices;

  @BeforeEach
  public void setup() {
    balanceServices = new BalanceServicesImpl();
  }

  @Test
  @DisplayName("Add money: Successful")
  public void testAddMoneySuccessful() {
    // Arrange
    String metroCardNumber = "123456789";
    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);
    userEntity.setBalance(100.0);
    double amountToAdd = 50.0;

    // Act
    balanceServices.addMoney(userEntity, amountToAdd);

    // Assert
    assertEquals(150.0, userEntity.getBalance());
  }

  @Test
  @DisplayName("Deduct money: successfull")
  public void testDeductFareSuccessfull() {
    // Arrange
    String metroCardNumber = "123456789";
    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);
    userEntity.setBalance(100.0);
    double fare = 50.0;

    // Act
    balanceServices.deductFare(userEntity, fare);

    // Assert
    assertEquals(50.0, userEntity.getBalance());
  }

  @Test
  @DisplayName("Deduct money: Insufficient Balance Exception")
  public void testDeductFareThrowInsufficientBalanceException() {
    // Arrange
    String metroCardNumber = "123456789";
    UserEntity userEntity = new UserEntity();
    userEntity.setMetroCardNumber(metroCardNumber);
    userEntity.setBalance(50.0);
    double fare = 150.0;

    // Assert
    assertThrows(InsufficientBalanceException.class, () -> balanceServices.deductFare(userEntity, fare));
  }

}
