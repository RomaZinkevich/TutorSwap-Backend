package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
