package com.zirom.tutorapi.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="skills")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Skill skill)) return false;
        return Objects.equals(id, skill.id) && Objects.equals(name, skill.name);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
