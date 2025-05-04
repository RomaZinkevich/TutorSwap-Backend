package com.zirom.tutorapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthUserResponse {
    private String message;
    private boolean isNewUser;
    private String token;
}
