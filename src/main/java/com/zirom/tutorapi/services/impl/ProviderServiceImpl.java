package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.Provider;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.repositories.ProviderRepository;
import com.zirom.tutorapi.services.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;

    @Override
    public Optional<User> getUserByProviderIdAndName(String providerId, String providerName) {
        return providerRepository.findProviderByProviderUserIdAndName(providerId, providerName).map(Provider::getUser);
    }

    @Override
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }
}
