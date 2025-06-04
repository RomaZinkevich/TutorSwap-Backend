package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.availability.DurationDto;
import com.zirom.tutorapi.domain.dtos.availability.PreferenceDto;
import com.zirom.tutorapi.domain.dtos.availability.request.DurationRequest;
import com.zirom.tutorapi.domain.dtos.availability.request.PreferenceRequest;
import com.zirom.tutorapi.domain.entities.availability.Duration;
import com.zirom.tutorapi.domain.entities.availability.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DurationMapper {
    DurationDto toDto(Duration duration);
    Duration toEntity(DurationRequest request);
}
