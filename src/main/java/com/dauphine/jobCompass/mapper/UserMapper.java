package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.SimpleUserDTO;
import com.dauphine.jobCompass.dto.UserCreationRequest;
import com.dauphine.jobCompass.dto.UserDTO;
import com.dauphine.jobCompass.model.User;
import org.mapstruct.Mapper;


import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    SimpleUserDTO toSimpleDto(User user);

    User toEntity(UserCreationRequest request);
    List<UserDTO> toDtoList(List<User> users);
}
