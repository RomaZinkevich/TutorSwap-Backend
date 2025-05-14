package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.VideoMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoMessageRepository extends JpaRepository<VideoMessage, UUID> {
}
