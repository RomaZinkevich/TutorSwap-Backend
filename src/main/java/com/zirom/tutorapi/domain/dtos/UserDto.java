package com.zirom.tutorapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String profileImage;
    private String description;
    private String universityName;
    private SkillDto skillToLearn;
    private SkillDto skillToTeach;
    private String wantToLearnDetail;
    private String wantToTeachDetail;
}
