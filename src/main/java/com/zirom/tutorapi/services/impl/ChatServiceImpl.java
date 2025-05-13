package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.repositories.ChatRepository;
import com.zirom.tutorapi.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
