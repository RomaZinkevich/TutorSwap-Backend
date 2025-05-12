package com.zirom.tutorapi.domain.dtos.auth.google;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleAuthRequest {

    @NotBlank(message = "id_token is required")
    private String idToken;
}