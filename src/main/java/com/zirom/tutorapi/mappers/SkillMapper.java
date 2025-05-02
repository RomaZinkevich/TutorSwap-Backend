package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.SkillDto;
import com.zirom.tutorapi.domain.entities.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {
    SkillDto toDto(Skill skill);
}
