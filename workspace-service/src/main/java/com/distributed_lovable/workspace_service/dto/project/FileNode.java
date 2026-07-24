package com.distributed_lovable.workspace_service.dto.project;

public record FileNode(
        String path
) {
    @Override
    public String toString() {
        return path;
    }
}
