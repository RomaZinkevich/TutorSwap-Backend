package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TextMessageRepository extends JpaRepository<TextMessage, UUID> {
}
