package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseMessageRepository extends JpaRepository<BaseMessage, UUID> {
}
