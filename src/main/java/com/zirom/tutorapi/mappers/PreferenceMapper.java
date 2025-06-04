package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.availability.PreferenceDto;
import com.zirom.tutorapi.domain.dtos.availability.ScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.PreferenceRequest;
import com.zirom.tutorapi.domain.entities.availability.Preference;
import com.zirom.tutorapi.domain.entities.availability.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PreferenceMapper {
    PreferenceDto toDto(Preference preference);
    Preference toEntity(PreferenceRequest request);
}