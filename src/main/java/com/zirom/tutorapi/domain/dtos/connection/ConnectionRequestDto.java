package com.zirom.tutorapi.domain.dtos.connection;

import com.zirom.tutorapi.domain.ConnectionType;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
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
    private UserDto senderUser;
    private String messageContent;
    private boolean accepted;
    private LocalDateTime createdAt;
    private ConnectionType connectionType;
}
