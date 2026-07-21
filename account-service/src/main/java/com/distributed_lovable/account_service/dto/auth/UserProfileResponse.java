package com.distributed_lovable.account_service.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String name
) {
}
