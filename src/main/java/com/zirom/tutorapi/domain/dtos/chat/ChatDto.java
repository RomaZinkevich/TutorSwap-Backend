package com.zirom.tutorapi.domain.dtos.chat;

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
    private UserDto senderUser;
    private UserDto receiverUser;
}
