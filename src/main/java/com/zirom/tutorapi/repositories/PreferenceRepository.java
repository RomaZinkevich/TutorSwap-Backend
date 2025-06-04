package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.availability.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
    Optional<Preference> findByUser_Id(UUID userId);
}
