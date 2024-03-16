package com.example.application.utils.Mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.application.dtos.bookingDTO.BookingDTO;
import com.example.application.dtos.userDTO.UserBasicDto;
import com.example.application.models.Booking;

@Component
public class BookingDTOMapper {
  private ModelMapper mapper = new ModelMapper();

  // Since UserEntity and UserBasicDto have different package locations, we need
  // to manually map the user field
  public BookingDTO map(Booking booking) {
    BookingDTO bookingResponse = mapper.map(booking, BookingDTO.class);

    UserBasicDto user = mapper.map(booking.getUser(), UserBasicDto.class);
    bookingResponse.setUser(user);

    return mapper.map(booking, BookingDTO.class);
  }
}
