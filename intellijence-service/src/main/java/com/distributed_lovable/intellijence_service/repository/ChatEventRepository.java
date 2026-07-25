package com.distributed_lovable.intellijence_service.repository;

import com.distributed_lovable.intellijence_service.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
}
