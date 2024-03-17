package com.example.application.utils.Mappers;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.application.dtos.bookingDTO.BookingBasicDTO;
import com.example.application.dtos.userDTO.UserDTO;
import com.example.application.models.UserEntity;

public class UserDTOMapper {
  private static ModelMapper mapper = new ModelMapper();

  public static UserDTO map(UserEntity user) {
    UserDTO userDTO = mapper.map(user, UserDTO.class);

    List<BookingBasicDTO> bookingBasicDTO = userDTO.getBookings()
        .stream()
        .map(booking -> mapper.map(booking, BookingBasicDTO.class))
        .collect(Collectors.toList());

    userDTO.setBookings(bookingBasicDTO);

    return mapper.map(user, UserDTO.class);
  }
}
