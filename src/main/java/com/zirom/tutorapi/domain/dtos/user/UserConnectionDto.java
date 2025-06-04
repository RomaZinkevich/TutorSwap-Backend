package com.zirom.tutorapi.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zirom.tutorapi.domain.RequestState;
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
    private String profileImage;
    private String description;
    private String universityName;
    private SkillDto skillToLearn;
    private SkillDto skillToTeach;
    private String wantToLearnDetail;
    private String wantToTeachDetail;
    private boolean connectionRequestExists;
    private RequestState requestState;
    @JsonProperty("isSender")
    private boolean isSender;
}
