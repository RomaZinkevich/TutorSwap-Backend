package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.Skill;

import java.util.UUID;

public interface SkillService {

    Skill findById(UUID id);
}
