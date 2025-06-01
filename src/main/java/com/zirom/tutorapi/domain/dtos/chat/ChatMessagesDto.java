package com.zirom.tutorapi.domain.dtos.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zirom.tutorapi.domain.dtos.chat.messages.MessageDto;
import com.zirom.tutorapi.domain.dtos.user.OtherUserDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessagesDto {
    private UUID id;
    private OtherUserDto user;
    private List<MessageDto> messages;

    @JsonProperty("isLearner")
    private boolean isLearner;
}
