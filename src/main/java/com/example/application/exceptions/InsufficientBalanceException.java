package com.example.application.exceptions;

public class InsufficientBalanceException extends RuntimeException {
  public InsufficientBalanceException() {
    super("Insufficient Balance! Please recharge to use Metro Services");
  }

  public InsufficientBalanceException(String message) {
    super(message);
  }
}
