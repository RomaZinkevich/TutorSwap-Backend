package com.zirom.tutorapi.domain.dtos;

import com.zirom.tutorapi.domain.entities.Skill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponse {
    private String message;
    private boolean isNewUser;
    private UUID id;
    private String email;
    private String name;
    private String description;
    private String universityName;
    private String profileImage;
    private Skill skillToLearn;
    private Skill skillToTeach;
    private String wantToLearnDetail;
    private String wantToTeachDetail;

}
