package com.example.application.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Fare of Travelling", description = "Fare of Travelling between two Stations")
public interface FareApi {

  @Operation(summary = "Get Fare", description = "Get fare of Travelling between two Stations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched fare"),
      @ApiResponse(responseCode = "400", description = "Bad Request if station id is invalid")
  })
  public ResponseEntity<Double> getFare(
      @Valid @RequestParam(required = true) String originStationId,
      @Valid @RequestParam(required = true) String destinationStationId);
}
