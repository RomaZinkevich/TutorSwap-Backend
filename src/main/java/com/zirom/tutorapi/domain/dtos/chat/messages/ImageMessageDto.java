package com.zirom.tutorapi.domain.dtos.chat.messages;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageMessageDto extends MessageDto {
    private String imageUrl;

    private String caption;
}
