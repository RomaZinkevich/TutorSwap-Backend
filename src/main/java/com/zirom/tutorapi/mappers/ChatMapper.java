package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.chat.ChatDto;
import com.zirom.tutorapi.domain.entities.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {
    public ChatDto toDto(Chat chat);
}
