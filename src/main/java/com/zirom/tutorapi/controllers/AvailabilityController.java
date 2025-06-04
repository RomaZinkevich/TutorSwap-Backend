package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.dtos.availability.AvailabilitySchedule;
import com.zirom.tutorapi.domain.dtos.availability.AvailabilityScheduleDto;
import com.zirom.tutorapi.domain.dtos.availability.request.AvailabilityScheduleRequest;
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

import java.util.UUID;

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
    public ResponseEntity<AvailabilityScheduleDto> getOwnAvailability(Authentication authentication) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        AvailabilitySchedule availabilitySchedule = availabilityService.getAvailabilitySchedule(user.getId());
        AvailabilityScheduleDto availabilityScheduleDto = ownAvailabilityScheduleMapper.toDto(availabilitySchedule);
        return new ResponseEntity<>(availabilityScheduleDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<AvailabilityScheduleDto> getOwnAvailability(@PathVariable UUID id) {
        AvailabilitySchedule availabilitySchedule = availabilityService.getAvailabilitySchedule(id);
        AvailabilityScheduleDto availabilityScheduleDto = ownAvailabilityScheduleMapper.toDto(availabilitySchedule);
        return new ResponseEntity<>(availabilityScheduleDto, HttpStatus.OK);
    }

    @PutMapping("/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<AvailabilityScheduleDto> updateOwnAvailability(
            Authentication authentication,
            @RequestBody AvailabilityScheduleRequest request
    ) {
        UserDto user = userService.getUserFromPrincipal((ApiUserDetails) authentication.getPrincipal());
        AvailabilitySchedule schedule = ownAvailabilityScheduleMapper.toEntity(request);
        AvailabilitySchedule newSchedule = availabilityService.changeOwnAvailabilitySchedule(schedule, user.getId());
        AvailabilityScheduleDto newScheduleDto = ownAvailabilityScheduleMapper.toDto(newSchedule);
        return new ResponseEntity<>(newScheduleDto, HttpStatus.OK);
    }
}
