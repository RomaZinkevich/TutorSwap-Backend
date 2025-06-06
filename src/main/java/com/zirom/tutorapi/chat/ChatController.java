package com.zirom.tutorapi.chat;

import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.edit.EditMessageRequest;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.mappers.MessageMapper;
import com.zirom.tutorapi.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Payload MessageRequest messageRequest,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        UUID senderId = UUID.fromString((String) headerAccessor.getSessionAttributes().get("id"));
        UUID chatId = messageRequest.getChatId();
        BaseMessage baseMessage = messageService.saveMessage(messageRequest, senderId);
        MessageDto messageDto = messageMapper.toDto(baseMessage, senderId);

        messagingTemplate.convertAndSend("/topic/chat." + chatId, messageDto);
    }

    @MessageMapping("/chat.editMessage")
    public void editMessage(
            @Payload EditMessageRequest editMessageRequest,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        UUID senderId = UUID.fromString((String) headerAccessor.getSessionAttributes().get("id"));
        BaseMessage baseMessage =  messageService.editMessage(senderId, editMessageRequest);
        MessageDto messageDto = messageMapper.toDto(baseMessage, senderId);

        messagingTemplate.convertAndSend("/topic/chat." + editMessageRequest.getChatId(), messageDto);
    }
}
