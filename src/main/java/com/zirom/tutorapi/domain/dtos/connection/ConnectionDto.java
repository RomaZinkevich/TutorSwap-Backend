package com.zirom.tutorapi.domain.dtos.connection;

import com.zirom.tutorapi.domain.dtos.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {
    private UUID id;

    private UserDto user;

    private UserDto partnerUser;
}
