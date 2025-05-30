package com.zirom.tutorapi.domain.dtos.connection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionRequest {

    @NotBlank(message = "Receiver's id is required")
    private UUID receiverId;

    @NotBlank(message = "Message is required")
    @Size(min = 2, max = 200, message = "Message must be between {min} and {max} characters")
    private String message;
}
