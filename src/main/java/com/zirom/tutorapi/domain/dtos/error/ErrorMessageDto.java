package com.zirom.tutorapi.domain.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessageDto {
    private String type = "error";
    private String message;
    private UUID senderId;
    private String chatId;
    private long timestamp = System.currentTimeMillis();
}