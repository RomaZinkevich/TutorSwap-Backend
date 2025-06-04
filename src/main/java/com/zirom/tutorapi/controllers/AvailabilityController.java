package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.availability.OwnAvailabilitySchedule;
import com.zirom.tutorapi.domain.dtos.availability.OwnAvailabilityScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.OwnAvailabilityScheduleRequest;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.mappers.OwnAvailabilityScheduleMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.AvailabilityService;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/availabilities")
@RequiredArgsConstructor
@Tag(name="Availability")
public class AvailabilityController {

    private final UserService userService;
    private final AvailabilityService availabilityService;
    private final OwnAvailabilityScheduleMapper ownAvailabilityScheduleMapper;

    @GetMapping("/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<OwnAvailabilityScheduleDto> getOwnAvailability(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        OwnAvailabilitySchedule availabilitySchedule = availabilityService.getOwnAvailabilitySchedule(user.getId());
        OwnAvailabilityScheduleDto availabilityScheduleDto = ownAvailabilityScheduleMapper.toDto(availabilitySchedule);
        return new ResponseEntity<>(availabilityScheduleDto, HttpStatus.OK);
    }

    @PutMapping("/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<OwnAvailabilityScheduleDto> updateOwnAvailability(
            Authentication authentication,
            @RequestBody OwnAvailabilityScheduleRequest request
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        OwnAvailabilitySchedule schedule = ownAvailabilityScheduleMapper.toEntity(request);
        OwnAvailabilitySchedule newSchedule = availabilityService.changeOwnAvailabilitySchedule(schedule, user.getId());
        OwnAvailabilityScheduleDto newScheduleDto = ownAvailabilityScheduleMapper.toDto(newSchedule);
        return new ResponseEntity<>(newScheduleDto, HttpStatus.OK);
    }
}
