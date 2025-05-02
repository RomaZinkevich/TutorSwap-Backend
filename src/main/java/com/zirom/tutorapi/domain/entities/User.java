package com.zirom.tutorapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;
import com.zirom.tutorapi.domain.entities.Skill;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "want_to_learn_skill_id", nullable = false)
    private Skill skillToLearn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "want_to_teach_skill_id", nullable = false)
    private Skill skillToTeach;

    @Column(nullable = false)
    private String wantToLearnDetail;

    @Column(nullable = false)
    private String wantToTeachDetail;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(description, user.description) && Objects.equals(universityName, user.universityName) && Objects.equals(profileImage, user.profileImage) && Objects.equals(skillToLearn, user.skillToLearn) && Objects.equals(skillToTeach, user.skillToTeach) && Objects.equals(wantToLearnDetail, user.wantToLearnDetail) && Objects.equals(wantToTeachDetail, user.wantToTeachDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, description, universityName, profileImage, skillToLearn, skillToTeach, wantToLearnDetail, wantToTeachDetail);
    }
}
