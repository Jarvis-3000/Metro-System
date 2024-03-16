package com.example.application.services.implementations;

import org.springframework.stereotype.Service;

import com.example.application.exceptions.InsufficientBalanceException;
import com.example.application.models.UserEntity;
import com.example.application.services.interfaces.BalanceServices;

@Service
public class BalanceServicesImpl implements BalanceServices {
  @Override
  public void deductFare(UserEntity user, double fare) throws InsufficientBalanceException {
    double userBalance = user.getBalance();

    if (userBalance < fare) {
      throw new InsufficientBalanceException();
    }

    // deduct money from user balance
    user.deduct(fare);
  }

  @Override
  public void addMoney(UserEntity user, double money) {
    user.recharge(money);
  }
}
