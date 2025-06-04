package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.lesson.ReservationDto;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    ReservationDto toDto(Reservation reservation);
}
