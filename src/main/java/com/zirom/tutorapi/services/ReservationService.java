package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.ReservationDirection;
import com.zirom.tutorapi.domain.dtos.lesson.ChangeReservationRequest;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<Reservation> getReservations(UUID id, RequestState state, ReservationDirection direction);

    Reservation changeReservation(UUID loggedInUserId, UUID reservationId, ChangeReservationRequest changeReservationRequest);

    Reservation createReservation(UUID userId, UUID learnerId, LocalDateTime timeStart, LocalDateTime timeEnd);
}
