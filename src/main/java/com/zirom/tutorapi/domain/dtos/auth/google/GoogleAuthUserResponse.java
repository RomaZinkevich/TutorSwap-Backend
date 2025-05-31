package com.zirom.tutorapi.domain.dtos.auth.google;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isNewUser")
    private boolean isNewUser;
    private String token;
}
