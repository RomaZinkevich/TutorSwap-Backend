package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.chat.ChatDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.mappers.ChatMapper;
import com.zirom.tutorapi.mappers.MessageMapper;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.repositories.ChatRepository;
import com.zirom.tutorapi.repositories.messages.BaseMessageRepository;
import com.zirom.tutorapi.services.ChatService;
import com.zirom.tutorapi.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;
    private final BaseMessageRepository baseMessageRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    @Transactional
    public List<ChatDto> getAllChatsByUserId(UUID userId) {
        List<Chat> chats = chatRepository.findAllByReceiverUserIdOrSenderUserId(userId);
        List<ChatDto> chatDtos = new ArrayList<>();
        chats.forEach(chat -> {
            BaseMessage lastMessage = baseMessageRepository.findBaseMessageByChat_IdOrderByTimestampDesc(chat.getId());
            MessageDto lastMessageDto = messageMapper.toDto(lastMessage, userId);
            ChatDto chatDto = chatMapper.toDto(chat);
            boolean isLearner = chat.getSenderUser().getId().equals(userId);
            chatDto.setLearner(isLearner);
            chatDto.setLastMessage(lastMessageDto);
            User user;
            if (chat.getSenderUser().getId().equals(userId)) {
                user = chat.getReceiverUser();
            } else {
                user = chat.getSenderUser();
            }
            UserDto userDto = userMapper.toDto(user);
            chatDto.setUser(userDto);
            chatDtos.add(chatDto);
        });
        return chatDtos;
    }

    @Override
    public Optional<Chat> getChatById(UUID chatId) {
        return chatRepository.findById(chatId);
    }
}
