package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.CreateSkillRequest;
import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.mappers.SkillMapper;
import com.zirom.tutorapi.services.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @GetMapping
    public ResponseEntity<List<SkillDto>> getSkills() {
        List<SkillDto> skillDtos = skillService.findAll().stream().map(skillMapper::toDto).toList();
        return new ResponseEntity<>(skillDtos, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable UUID id) {
        SkillDto skillDto = skillMapper.toDto(skillService.findById(id));
        return new ResponseEntity<>(skillDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<SkillDto> createSkill(@RequestBody @Valid CreateSkillRequest createSkillRequest) {
        Skill skillToCreate = skillMapper.toEntity(createSkillRequest);
        Skill savedSKill = skillService.create(skillToCreate);
        return new ResponseEntity<>(skillMapper.toDto(savedSKill), HttpStatus.CREATED);
    }
}
