package com.example.application.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.application.dtos.stationDTO.AddStationRequest;
import com.example.application.models.Station;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Stations", description = "Station Api Endpoints")
public interface StationApi {

  @Operation(summary = "Add a new Station", description = "Admin can add a new station with new name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully added a new station"),
      @ApiResponse(responseCode = "400", description = "Invalid arguments or Station already exist with name"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<Station> add(@Valid @RequestBody AddStationRequest addStationRequest);

  @Operation(summary = "Get all stations", description = "Users can fetch all the stations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched all the stations"),
  })
  public ResponseEntity<List<Station>> getAllStations();

  @Operation(summary = "Get Station By Id", description = "Users can fetch station by stationId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched station by stationId"),
      @ApiResponse(responseCode = "400", description = "Invalid arguments or Station not found by id")
  })
  public ResponseEntity<Station> getById(@Valid @PathVariable String id);

  @Operation(summary = "Remove a station", description = "Admin can remove a station by stationId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully removed the station"),
      @ApiResponse(responseCode = "400", description = "Invalid arguments or Station not found by id"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<?> removeById(@Valid @PathVariable String id);

  @Operation(summary = "Remove all the stations", description = "Admin can remove all stations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully removed all the stations"),
      @ApiResponse(responseCode = "401", description = "Not Authorized")
  })
  public ResponseEntity<?> removeAll();
}
