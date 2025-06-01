package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.chat.ChatMessagesDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<BaseMessage> getAllMessagesByChatAndUserIds(UUID userId, UUID chatId);
    BaseMessage saveTextMessage(TextMessageRequest messageRequest, UUID senderId);
    BaseMessage getLastMessageByChatId(UUID chatId);
}
