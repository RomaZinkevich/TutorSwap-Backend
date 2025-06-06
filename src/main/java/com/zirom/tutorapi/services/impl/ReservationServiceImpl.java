package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.RequestState;
import com.zirom.tutorapi.domain.ReservationDirection;
import com.zirom.tutorapi.domain.dtos.lesson.ChangeReservationRequest;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.domain.entities.lesson.Reservation;
import com.zirom.tutorapi.repositories.ReservationRepository;
import com.zirom.tutorapi.repositories.UserRepository;
import com.zirom.tutorapi.services.AuthorizationService;
import com.zirom.tutorapi.services.LessonService;
import com.zirom.tutorapi.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final AuthorizationService authorizationService;
    private final LessonService lessonService;
    private final UserRepository userRepository;

    @Override
    public List<Reservation> getReservations(UUID id, RequestState state, ReservationDirection direction) {
        if (direction == ReservationDirection.TEACHER)
            return reservationRepository.findAllByUser_IdAndState(id, state);
        else
            return reservationRepository.findAllByLearner_IdAndState(id, state);
    }

    @Override
    @Transactional
    public Reservation changeReservation(UUID loggedInUserId, UUID reservationId, ChangeReservationRequest changeReservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
        if (reservation.getState() != RequestState.PENDING) throw new IllegalArgumentException("Reservation is not pending.");
        System.out.println(changeReservationRequest.getGoogleMeetUrl());
        System.out.println(changeReservationRequest.isAccepted());
        boolean isAccepted = changeReservationRequest.isAccepted();
        String googleMeetUrl = changeReservationRequest.getGoogleMeetUrl();
        authorizationService.canChangeReservation(reservation, loggedInUserId);
        reservation.setState(isAccepted ? RequestState.ACCEPTED : RequestState.REJECTED);
        if (isAccepted) lessonService.createLesson(reservation, googleMeetUrl);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation createReservation(UUID userId, UUID learnerId, LocalDateTime timeStart, LocalDateTime timeEnd) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        User learner = userRepository.findById(learnerId).orElseThrow(EntityNotFoundException::new);
        Reservation newReservation = new Reservation();
        newReservation.setUser(user);
        newReservation.setLearner(learner);
        newReservation.setTimeStart(timeStart);
        newReservation.setTimeEnd(timeEnd);
        return reservationRepository.save(newReservation);
    }
}
