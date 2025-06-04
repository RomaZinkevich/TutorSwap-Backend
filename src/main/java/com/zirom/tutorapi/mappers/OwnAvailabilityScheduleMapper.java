package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.availability.OwnAvailabilitySchedule;
import com.zirom.tutorapi.domain.dtos.availability.OwnAvailabilityScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.OwnAvailabilityScheduleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnAvailabilityScheduleMapper {
    OwnAvailabilityScheduleDto toDto(OwnAvailabilitySchedule availabilitySchedule);
    OwnAvailabilitySchedule toEntity(OwnAvailabilityScheduleRequest request);
}
