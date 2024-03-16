package com.example.application.dtos.errorDTO;

import java.util.Map;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
  private Map<String, String> error;
  private HttpStatusCode statusCode;
}
