package com.distributed_lovable.intellijence_service.service;


import com.distributed_lovable.intellijence_service.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getProjectChatHistory(Long projectId);
}
