package com.distributed_lovable.account_service.mapper;


import com.distributed_lovable.account_service.dto.auth.SignUpRequest;
import com.distributed_lovable.account_service.dto.auth.UserProfileResponse;
import com.distributed_lovable.account_service.entity.User;
import com.distributed_lovable.common_lib.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignUpRequest signUpRequest);

    UserProfileResponse toUserProfileResponse(User user);

    UserDto toUserDto(User user);
}
