package com.zirom.tutorapi.domain.dtos.user;

import com.zirom.tutorapi.domain.ConnectionRequestState;
import com.zirom.tutorapi.domain.dtos.skill.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConnectionDto {
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
    private boolean connectionRequestExists;
    private ConnectionRequestState requestState;
    private boolean isSender;
}
