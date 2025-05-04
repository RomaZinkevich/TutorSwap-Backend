package com.zirom.tutorapi.repositories;

import com.zirom.tutorapi.domain.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {

    Optional<Provider> findProviderByProviderUserIdAndName(String providerUserId, String providerName);
}
