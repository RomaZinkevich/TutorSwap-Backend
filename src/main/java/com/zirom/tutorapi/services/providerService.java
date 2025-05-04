package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.Provider;
import com.zirom.tutorapi.domain.entities.User;

import java.util.Optional;

public interface providerService {
    Optional<User> getUserByProviderIdAndName(String providerId, String providerName);
    Provider save(Provider provider);
}
