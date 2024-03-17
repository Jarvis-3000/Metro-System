package com.example.application.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "Balance", description = "User Balance Endpoints")
public interface BalanceApi {
  @Operation(summary = "Add Balance", description = "Add Balance in user account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully added balance")
  })
  public ResponseEntity<String> addBalance(HttpServletRequest request,
      @Valid @Min(value = 0, message = "Amount must be positive") @RequestParam(required = true) Double money);

  @Operation(summary = "Get Balance", description = "Get Balance of user account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved user balance")
  })
  public ResponseEntity<Double> getBalance(HttpServletRequest request);
}
