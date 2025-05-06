package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.repositories.SkillRepository;
import com.zirom.tutorapi.services.SkillService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public Skill findById(UUID id) {
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Skill not found with id: " + id));
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill create(Skill skillToCreate) {
        return skillRepository.save(skillToCreate);
    }
}
