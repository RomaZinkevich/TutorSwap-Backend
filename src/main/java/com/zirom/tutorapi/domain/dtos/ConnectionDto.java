package com.zirom.tutorapi.domain.dtos;

import com.zirom.tutorapi.domain.ConnectionType;
import com.zirom.tutorapi.domain.entities.User;
import jakarta.persistence.*;
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

    private ConnectionType connectionType;
}
