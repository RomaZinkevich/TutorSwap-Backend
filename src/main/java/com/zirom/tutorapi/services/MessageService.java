package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.dtos.chat.ChatMessagesDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.ImageMessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit.EditMessageRequest;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    BaseMessage saveMessage(MessageRequest messageRequest, UUID senderId);
    BaseMessage editMessage(UUID loggedInUserId, EditMessageRequest editMessageRequest);
}
