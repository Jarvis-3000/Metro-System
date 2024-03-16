package com.example.application.dtos.bookingDTO;


import com.example.application.dtos.authDTO.RegisterUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RegisterUserRequest DTO Tests")
public class RegisterUserRequestTest {

  @Test
  @DisplayName("Constructor Test")
  public void testConstructor() {
    // Arrange
    String holderName = "John Doe";
    String password = "Password123";
    boolean admin = true;

    // Act
    RegisterUserRequest request = new RegisterUserRequest(holderName, password, admin);

    // Assert
    assertEquals(holderName, request.getHolderName());
    assertEquals(password, request.getPassword());
    assertEquals(admin, request.isAdmin());
  }
}
