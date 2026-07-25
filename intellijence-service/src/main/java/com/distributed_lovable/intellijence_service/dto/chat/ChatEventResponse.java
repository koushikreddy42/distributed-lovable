package com.distributed_lovable.intellijence_service.dto.chat;


import com.distributed_lovable.common_lib.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
