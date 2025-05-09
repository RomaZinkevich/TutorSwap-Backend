package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.ConnectionRequestDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectionRequestMapper {
    ConnectionRequestDto toDto(ConnectionRequest connectionRequest);
}
