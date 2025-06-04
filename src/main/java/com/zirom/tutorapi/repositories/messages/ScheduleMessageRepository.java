package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.ImageMessage;
import com.zirom.tutorapi.domain.entities.messages.ScheduleMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleMessageRepository extends JpaRepository<ScheduleMessage, UUID> {
}
