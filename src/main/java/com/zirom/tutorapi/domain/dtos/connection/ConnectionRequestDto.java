package com.zirom.tutorapi.domain.dtos.connection;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.dtos.user.OtherUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConnectionRequestDto {
    private UUID id;
    private OtherUserDto senderUser;
    private String messageContent;
    private RequestState requestState;
    private LocalDateTime createdAt;
}
