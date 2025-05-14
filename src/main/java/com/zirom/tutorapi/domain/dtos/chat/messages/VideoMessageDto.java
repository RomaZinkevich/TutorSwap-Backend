package com.zirom.tutorapi.domain.dtos.chat.messages;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoMessageDto extends MessageDto {
    private String videoUrl;
    private String caption;
}
