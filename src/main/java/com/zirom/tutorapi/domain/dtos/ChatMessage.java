package com.zirom.tutorapi.domain.dtos;

import com.zirom.tutorapi.domain.MessageType;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
}
