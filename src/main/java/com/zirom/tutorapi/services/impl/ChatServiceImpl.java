package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.repositories.ChatRepository;
import com.zirom.tutorapi.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getAllChatsByUserId(UUID userId) {
        return chatRepository.findAllByReceiverUserIdOrSenderUserId(userId);
    }

    @Override
    public Optional<Chat> getChatById(UUID chatId) {
        return chatRepository.findById(chatId);
    }
}
