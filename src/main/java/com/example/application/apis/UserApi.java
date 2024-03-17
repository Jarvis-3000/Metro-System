package com.example.application.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.application.dtos.userDTO.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users", description = "User api endpoints")
public interface UserApi {
  @Operation(summary = "Get All users", description = "Admin can fetch all the users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched all the users"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<List<UserDTO>> getAll();

  @Operation(summary = "Get user by metroCardNumber", description = "Admin can fetch user by metroCardNUmber")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched the user"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<UserDTO> getByMetroCardNumber(@Valid @PathVariable String metroCardNumber);

  @Operation(summary = "Delete user", description = "Admin can delete a user by metroCardNUmber")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<String> deleteByMetroCardNumber(@Valid @PathVariable String metroCardNumber);

  @Operation(summary = "Delete all users", description = "Admin can delete all the users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted all the users"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<String> deleteAll();
}
