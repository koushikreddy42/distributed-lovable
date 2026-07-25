package com.distributed_lovable.intellijence_service.service;

import com.distributed_lovable.intellijence_service.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
