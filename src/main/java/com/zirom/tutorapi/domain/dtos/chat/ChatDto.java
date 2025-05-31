package com.zirom.tutorapi.domain.dtos.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {
    private UUID id;
    private UserDto user;
    private MessageDto lastMessage;

    @JsonProperty("isLearner")
    private boolean isLearner;
}
