package com.zirom.tutorapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestDto {
    @NotBlank(message = "name is required")
    @Size(min = 2, max = 200, message = "Name must be between {min} and {max} characters")
    private String name;

    @NotBlank(message = "profileImage is required")
    private String profileImage;

    @NotBlank(message = "description is required")
    @Size(min = 2, max = 2000, message = "Description must be between {min} and {max} characters")
    private String description;

    @NotBlank(message = "university_name is required")
    @Size(min = 2, max = 50, message = "University_name must be between {min} and {max} characters")
    private String universityName;

    @NotNull(message = "skill_to_learn_id is required")
    private UUID skillToLearnId;

    @NotNull(message = "skill_to_teach_id is required")
    private UUID skillToTeachId;

    @NotBlank(message = "skill_to_learn_details is required")
    @Size(min = 2, max = 2000, message = "skill_to_learn_details must be between {min} and {max} characters")
    private String wantToLearnDetail;

    @NotBlank(message = "skill_to_teach_detail is required")
    @Size(min = 2, max = 2000, message = "skill_to_teach_details must be between {min} and {max} characters")
    private String wantToTeachDetail;
}
