package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.chat.ChatDto;
import com.zirom.tutorapi.domain.entities.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {
    Chat createChat(Chat chat);
    List<ChatDto> getAllChatsByUserId(UUID userId);
    Optional<Chat> getChatById(UUID chatId);
}
