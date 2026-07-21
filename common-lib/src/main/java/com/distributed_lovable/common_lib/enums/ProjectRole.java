package com.distributed_lovable.common_lib.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.distributed_lovable.common_lib.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {

    EDITOR(VIEW, EDIT, DELETE, VIEW_MEMBERS), // we can do this since we wrote custom constructor which converts to set
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)), // or we can directly pass set here as well
    OWNER(Set.of(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS));

    ProjectRole(ProjectPermission... permissions){
        this.permissions = Set.of(permissions);
    }

    private final Set<ProjectPermission> permissions;
}
