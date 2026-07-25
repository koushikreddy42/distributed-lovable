package com.distributed_lovable.intellijence_service.repository;

import com.distributed_lovable.intellijence_service.entity.ChatSession;
import com.distributed_lovable.intellijence_service.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
