package com.zirom.tutorapi.controllers;

import com.zirom.tutorapi.domain.ConnectionRequestDirection;
import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.ReservationDirection;
import com.zirom.tutorapi.domain.dtos.connection.ConnectionRequestDto;
import com.zirom.tutorapi.domain.dtos.lesson.ReservationDto;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.ConnectionRequest;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.mappers.ReservationMapper;
import com.zirom.tutorapi.security.ApiUserDetails;
import com.zirom.tutorapi.services.ReservationService;
import com.zirom.tutorapi.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/v1/reservations")
@RequiredArgsConstructor
@Tag(name="Reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @GetMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<ReservationDto>> getReservations(
            @RequestParam(required = false, defaultValue = "ACCEPTED") RequestState requestState,
            @RequestParam(required = false, defaultValue = "TEACHER") ReservationDirection direction,
            @PathVariable UUID id
    ) {
        List<Reservation> reservations = reservationService.getReservations(id, requestState, direction);
        List<ReservationDto> dtos = reservations.stream().map(reservationMapper::toDto).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
