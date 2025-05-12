package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.skill.CreateSkillRequest;
import com.zirom.tutorapi.domain.dtos.skill.SkillDto;
import com.zirom.tutorapi.domain.entities.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {
    SkillDto toDto(Skill skill);
    Skill toEntity(SkillDto skillDto);
    Skill toEntity(CreateSkillRequest createSkillRequest);
}
