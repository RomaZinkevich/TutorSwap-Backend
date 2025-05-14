package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BaseMessageRepository extends JpaRepository<BaseMessage, UUID> {
    @Query("SELECT m FROM BaseMessage m WHERE (m.sender.id = :userId OR m.receiver.id = :userId) AND m.chat.id = :chatId")
    List<BaseMessage> findAllMessagesByChatIdAndSenderOrReceiverIds(UUID userId, UUID chatId);
}
