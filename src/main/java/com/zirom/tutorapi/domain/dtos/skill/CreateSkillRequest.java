package com.zirom.tutorapi.domain.dtos.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSkillRequest {

    @NotBlank(message = "Skill name is required")
    @Size(min = 2, max = 50, message = "Skill name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Skill name can only contain letters, numbers, spaces and hyphens")
    private String name;
}
