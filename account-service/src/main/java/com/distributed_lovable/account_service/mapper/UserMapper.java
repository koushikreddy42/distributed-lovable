package com.distributed_lovable.account_service.mapper;


import com.distributed_lovable.account_service.dto.auth.SignUpRequest;
import com.distributed_lovable.account_service.dto.auth.UserProfileResponse;
import com.distributed_lovable.account_service.entity.User;
import com.distributed_lovable.common_lib.dto.UserDto;
import com.distributed_lovable.common_lib.security.JwtUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignUpRequest signUpRequest);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(JwtUserPrincipal user);

    UserDto toUserDto(User user);
}
