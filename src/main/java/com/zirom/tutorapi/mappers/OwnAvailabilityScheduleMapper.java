package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.availability.AvailabilitySchedule;
import com.zirom.tutorapi.domain.dtos.availability.AvailabilityScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.AvailabilityScheduleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnAvailabilityScheduleMapper {
    AvailabilityScheduleDto toDto(AvailabilitySchedule availabilitySchedule);
    AvailabilitySchedule toEntity(AvailabilityScheduleRequest request);
}
