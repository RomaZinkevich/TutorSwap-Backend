package com.zirom.tutorapi.domain.dtos;

import com.zirom.tutorapi.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String profileImage;
    private String description;
    private String universityName;
    private SkillDto skillToLearn;
    private SkillDto skillToTeach;
    private String wantToLearnDetail;
    private String wantToTeachDetail;
    private Role role;
}
