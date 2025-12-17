package com.example.user_service.common;

import com.example.user_service.dao.User;
import com.example.user_service.dto.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest request);
}
