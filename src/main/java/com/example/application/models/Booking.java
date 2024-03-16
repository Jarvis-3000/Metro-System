package com.example.application.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.application.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {
  @Id
  private String id;

  @NotNull
  @DBRef
  @JsonIgnoreProperties("bookings") // Add this annotation
  private UserEntity user;

  @NotNull
  @DBRef
  private Station originStation;

  @NotNull
  @DBRef
  private Station destinationStation;

  @NotNull
  private LocalDateTime dateTime;

  @Min(0)
  private double fare;

  @NotNull
  private BookingStatus bookingStatus;
}
