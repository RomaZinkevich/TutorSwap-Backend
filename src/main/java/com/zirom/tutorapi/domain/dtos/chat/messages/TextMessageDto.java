package com.zirom.tutorapi.domain.dtos.chat.messages;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageDto extends MessageDto {
    private String content;
}
