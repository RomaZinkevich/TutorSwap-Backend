package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.ReservationDirection;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.repositories.ReservationRepository;
import com.zirom.tutorapi.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getReservations(UUID id, RequestState state, ReservationDirection direction) {
        if (direction == ReservationDirection.TEACHER)
            return reservationRepository.findAllByUser_IdAndState(id, state);
        else
            return reservationRepository.findAllByLearner_IdAndState(id, state);
    }
}
