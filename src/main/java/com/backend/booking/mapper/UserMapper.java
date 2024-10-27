package com.backend.booking.mapper;

import com.backend.booking.model.dto.request.UserCreateOrUpdateDto;
import com.backend.booking.model.dto.response.UserDetailsDto;
import com.backend.booking.model.dto.response.UserDto;
import com.backend.booking.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetailsDto toUserDetailsDto(User user);
    UserDto toUserDto(User user);
    User toUser(UserCreateOrUpdateDto userDto);
}
