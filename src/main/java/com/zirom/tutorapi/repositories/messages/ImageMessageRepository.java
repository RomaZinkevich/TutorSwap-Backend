package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.ImageMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageMessageRepository extends JpaRepository<ImageMessage, UUID> {
}
