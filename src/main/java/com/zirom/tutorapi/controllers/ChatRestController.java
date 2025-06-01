package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.chat.ChatDto;
import com.zirom.tutorapi.domain.dtos.chat.ChatMessagesDto;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Chat;
import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.mappers.ChatMapper;
import com.zirom.tutorapi.mappers.MessageMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.ChatService;
import com.zirom.tutorapi.services.MessageService;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/chats")
@RequiredArgsConstructor
@Tag(name="Chat")
public class ChatRestController {

    private final UserService userService;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @GetMapping
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<ChatDto>> getChats(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        List<ChatDto> chatDtos = chatService.getAllChatsByUserId(user.getId());
        return new ResponseEntity<>(chatDtos, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ChatMessagesDto> getMessagesByChatId(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        UUID userId = user.getId();
        ChatMessagesDto messages = chatService.getChatAndMessagesById(id, userId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
