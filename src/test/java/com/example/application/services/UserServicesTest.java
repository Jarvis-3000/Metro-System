package com.example.application.services;

import com.example.application.dtos.authDTO.RegisterUserRequest;
import com.example.application.enums.Role;
import com.example.application.models.UserEntity;
import com.example.application.repositories.UserRepository;
import com.example.application.services.implementations.UserServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserServicesImpl Tests")
public class UserServicesTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServicesImpl userServices;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Test register user")
  public void testRegisterUser() {
    // Arrange
    RegisterUserRequest registerUserRequest = new RegisterUserRequest();
    registerUserRequest.setHolderName("John Doe");
    registerUserRequest.setPassword("password");
    registerUserRequest.setAdmin(false);

    UserEntity expectedUser = new UserEntity();
    expectedUser.setHolderName(registerUserRequest.getHolderName());
    expectedUser.setPassword("encodedPassword");
    expectedUser.setCreatedAt(LocalDateTime.now());
    expectedUser.setBalance(1000);
    expectedUser.setBookings(new ArrayList<>());
    expectedUser.setRoles(Collections.singleton(Role.USER));

    // Mock passwordEncoder encode method
    when(passwordEncoder.encode(registerUserRequest.getPassword())).thenReturn("encodedPassword");

    // Mock userRepository save method
    when(userRepository.save(any())).thenReturn(expectedUser);

    // Act
    UserEntity savedUser = userServices.register(registerUserRequest);

    // Assert
    assertNotNull(savedUser);
    assertEquals(expectedUser.getHolderName(), savedUser.getHolderName());
    assertEquals(expectedUser.getPassword(), savedUser.getPassword());
    assertEquals(expectedUser.getCreatedAt(), savedUser.getCreatedAt());
    assertEquals(expectedUser.getBalance(), savedUser.getBalance());
    assertEquals(expectedUser.getBookings().size(), savedUser.getBookings().size());
    assertEquals(expectedUser.getRoles(), savedUser.getRoles());
  }

  @Test
  @DisplayName("Test find all users")
  public void testFindAllUsers() {
    // Arrange
    List<UserEntity> expectedUsers = new ArrayList<>();

    expectedUsers
        .add(new UserEntity("123", "password", "John Doe", 1000, Set.of(Role.ADMIN), LocalDateTime.now(),
            new ArrayList<>()));
    expectedUsers
        .add(new UserEntity("143", "password", "Jane Doe", 1000, Set.of(Role.ADMIN, Role.USER), LocalDateTime.now(),
            new ArrayList<>()));

    // Mock userRepository findAll method
    when(userRepository.findAll()).thenReturn(expectedUsers);

    // Act
    List<UserEntity> actualUsers = userServices.findAll();

    // Assert
    assertEquals(expectedUsers.size(), actualUsers.size());
    assertEquals(expectedUsers, actualUsers);
  }

  @Test
  @DisplayName("Test find user by metro card number - Found")
  public void testFindUserByMetroCardNumber_Found() {
    // Arrange
    String metroCardNumber = "1234567890";
    UserEntity expectedUser = new UserEntity("123", "John Doe", "password", 1000, Set.of(Role.ADMIN),
        LocalDateTime.now(), new ArrayList<>());

    // Mock userRepository findByMetroCardNumber method
    when(userRepository.findByMetroCardNumber(metroCardNumber)).thenReturn(Optional.of(expectedUser));

    // Act
    UserEntity actualUser = userServices.findByMetroCardNumber(metroCardNumber);

    // Assert
    assertNotNull(actualUser);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  @DisplayName("Test find user by metro card number - Not Found")
  public void testFindUserByMetroCardNumber_NotFound() {
    // Arrange
    String metroCardNumber = "1234567890";

    // Mock userRepository findByMetroCardNumber method
    when(userRepository.findByMetroCardNumber(metroCardNumber)).thenReturn(Optional.empty());

    // Assert
    assertThrows(ResponseStatusException.class, () -> userServices.findByMetroCardNumber(metroCardNumber));
  }

  @Test
  @DisplayName("Test exists by metro card number - True")
  public void testExistsByMetroCardNumber_True() {
    // Arrange
    String metroCardNumber = "1234567890";

    // Mock userRepository existsByMetroCardNumber method
    when(userRepository.existsByMetroCardNumber(metroCardNumber)).thenReturn(true);

    // Act
    boolean exists = userServices.existsByMetroCardNumber(metroCardNumber);

    // Assert
    assertTrue(exists);
  }

  @Test
  @DisplayName("Test exists by metro card number - False")
  public void testExistsByMetroCardNumber_False() {
    // Arrange
    String metroCardNumber = "1234567890";

    // Mock userRepository existsByMetroCardNumber method
    when(userRepository.existsByMetroCardNumber(metroCardNumber)).thenReturn(false);

    // Act
    boolean exists = userServices.existsByMetroCardNumber(metroCardNumber);

    // Assert
    assertFalse(exists);
  }

  @Test
  @DisplayName("Test delete user by metro card number - Found")
  public void testDeleteUserByMetroCardNumber_Found() {
    // Arrange
    String metroCardNumber = "1234567890";

    // Mock userRepository existsByMetroCardNumber method
    when(userRepository.existsByMetroCardNumber(metroCardNumber)).thenReturn(true);

    // Act
    assertDoesNotThrow(() -> userServices.deleteByMetroCardNumber(metroCardNumber));

    // Verify userRepository deleteByMetroCardNumber method is called
    verify(userRepository, times(1)).deleteByMetroCardNumber(metroCardNumber);
  }

  @Test
  @DisplayName("Test delete user by metro card number - Not Found")
  public void testDeleteUserByMetroCardNumber_NotFound() {
    // Arrange
    String metroCardNumber = "1234567890";

    // Mock userRepository existsByMetroCardNumber method
    when(userRepository.existsByMetroCardNumber(metroCardNumber)).thenReturn(false);

    // Assert
    assertThrows(ResponseStatusException.class, () -> userServices.deleteByMetroCardNumber(metroCardNumber));
  }
}
