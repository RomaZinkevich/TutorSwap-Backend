package com.zirom.tutorapi.mappers;

import com.zirom.tutorapi.domain.dtos.UpdateUserRequestDto;
import com.zirom.tutorapi.domain.dtos.UserDto;
import com.zirom.tutorapi.domain.entities.User;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "skillToLearn", source = "skillToLearn")
    @Mapping(target = "skillToTeach", source = "skillToTeach")
    UserDto toDto(User user);
}
