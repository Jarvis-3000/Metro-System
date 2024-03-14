package com.example.application.services.interfaces;

import com.example.application.exceptions.InsufficientBalanceException;
import com.example.application.models.UserEntity;

public interface BalanceServices {
  public void deductFare(UserEntity user, double fare) throws InsufficientBalanceException;

  public void addMoney(UserEntity user, double money);
}
