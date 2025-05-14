package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.Chat;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    Chat createChat(Chat chat);
    List<Chat> getAllChatsByUserId(UUID userId);
}
