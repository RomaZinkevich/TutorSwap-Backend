package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    BaseMessage saveTextMessage(BaseMessage baseMessage, String content);
    List<BaseMessage> getAllMessagesByChatAndUserIds(UUID userId, UUID chatId);
}
