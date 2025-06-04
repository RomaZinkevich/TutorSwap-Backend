package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.availability.ScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.ScheduleRequest;
import com.zirom.tutorapi.domain.entities.availability.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {
    ScheduleDto toDto(Schedule schedule);
    Schedule toEntity(ScheduleRequest request);
}
