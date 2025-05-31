package com.zirom.tutorapi.chat;

import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.MessageRequest;
import com.zirom.tutorapi.domain.dtos.chat.messages.requests.TextMessageRequest;
import com.zirom.tutorapi.domain.dtos.error.ErrorMessageDto;
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
        UUID senderId = (UUID) headerAccessor.getSessionAttributes().get("id");
        String chatId = String.valueOf(messageRequest.getChatId());
        MessageDto messageDto;
        if (messageRequest instanceof TextMessageRequest text) {
            BaseMessage baseMessage = messageService.saveTextMessage(text, senderId);
            messageDto = messageMapper.toDto(baseMessage, senderId);
        }
        else {
            ErrorMessageDto error = new ErrorMessageDto().builder()
                    .chatId(chatId)
                    .message("Invalid message format")
                    .senderId(senderId)
                    .build();
            messagingTemplate.convertAndSend("/topic/chat." + chatId, error);
            return;
        }

        messagingTemplate.convertAndSend("/topic/chat." + chatId, messageDto);
    }
}
