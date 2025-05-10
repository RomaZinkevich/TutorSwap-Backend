package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.ConnectionDto;
import com.zirom.tutorapi.domain.entities.Connection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectionMapper {
    ConnectionDto toDto(Connection connection);
}
