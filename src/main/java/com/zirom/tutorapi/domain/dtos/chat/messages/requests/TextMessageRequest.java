package com.zirom.tutorapi.domain.dtos.chat.messages.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageRequest extends MessageRequest {

    @NotBlank
    @Size(max = 1000)
    private String content;
}
