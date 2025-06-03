package com.zirom.tutorapi.repositories.messages;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BaseMessageRepository extends JpaRepository<BaseMessage, UUID> {

    //Get last message from the chat
    BaseMessage findTopByChat_IdOrderByTimestampDesc(UUID chatId);

    List<BaseMessage> findAllByChat_IdOrderByTimestampAsc(UUID chatId);
}
