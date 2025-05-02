package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
