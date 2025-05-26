package com.zirom.tutorapi.chat;

import com.zirom.tutorapi.domain.dtos.ChatMessage;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        System.out.println(chatMessage.getContent());
        System.out.println(chatMessage.getReceiver());
        System.out.println(chatMessage.getSender());
        System.out.println(chatMessage.getType());
        String senderId = (String) headerAccessor.getSessionAttributes().get("id");
        User sender = userService.findById(UUID.fromString(senderId)).orElseThrow();
        String name = sender.getName();
        chatMessage.setSender(name);
        String receiver = chatMessage.getReceiver();
        messagingTemplate.convertAndSend("/topic/private." + receiver, chatMessage);
    }
}
