package com.bobby.onlinestore.mapper;

import com.bobby.onlinestore.Dtos.RegisterUserRequest;
import com.bobby.onlinestore.Dtos.UpdateUserRequest;
import com.bobby.onlinestore.Dtos.UserDto;
import com.bobby.onlinestore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);
    void updateRequest(UpdateUserRequest request, @MappingTarget User user);
}
