package com.distributed_lovable.workspace_service.dto.project;

import com.distributed_lovable.common_lib.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectRole role,
        Instant createdAt,
        Instant updatedAt
) {
}
