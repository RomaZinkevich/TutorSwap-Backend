package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.LessonRequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRequestMessageRepository extends JpaRepository<LessonRequestMessage, UUID> {
}
