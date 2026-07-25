package com.distributed_lovable.intellijence_service.mapper;


import com.distributed_lovable.intellijence_service.dto.chat.ChatResponse;
import com.distributed_lovable.intellijence_service.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);
}
