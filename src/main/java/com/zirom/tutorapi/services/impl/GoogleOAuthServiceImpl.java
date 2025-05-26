package com.zirom.tutorapi.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.zirom.tutorapi.domain.Role;
import com.zirom.tutorapi.domain.dtos.auth.SignUpRequest;
import com.zirom.tutorapi.domain.dtos.auth.google.GoogleTokenResponse;
import com.zirom.tutorapi.domain.dtos.auth.google.GoogleUserInfo;
import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.domain.entities.Provider;
import com.zirom.tutorapi.domain.entities.Skill;
import com.zirom.tutorapi.domain.entities.User;
import com.zirom.tutorapi.mappers.UserMapper;
import com.zirom.tutorapi.services.GoogleOAuthService;
import com.zirom.tutorapi.services.SkillService;
import com.zirom.tutorapi.services.UserService;
import com.zirom.tutorapi.services.ProviderService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleOAuthServiceImpl implements GoogleOAuthService {
    private final ProviderService providerService;
    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_EXPIRY_MS}")
    private Long jwtExpiryMs;

    private GoogleIdTokenVerifier verifier;
    private final SkillService skillService;
    private final UserService userService;

    @PostConstruct
    private void init() {
        JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
        NetHttpTransport transport = new NetHttpTransport();

        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public Optional<User> googleAuthenticate(String idToken) {
        GoogleUserInfo userInfo = verifyAndParseIdToken(idToken);
        return providerService.getUserByProviderIdAndName(userInfo.getSub(), "google");
    }

    @Override
    @Transactional
    public User signup(SignUpRequest dto) {
        GoogleUserInfo userInfo = verifyAndParseIdToken(dto.getIdToken());
        User user = new User();
        user.setEmail(userInfo.getEmail());
        user.setName(dto.getName());
        user.setProfileImage(dto.getProfileImage());
        user.setDescription(dto.getDescription());
        user.setUniversityName(dto.getUniversityName());
        user.setWantToLearnDetail(dto.getWantToLearnDetail());
        user.setWantToTeachDetail(dto.getWantToTeachDetail());
        user.setRole(Role.USER);

        Skill skillToLearn = skillService.findById(dto.getSkillToLearnId());
        user.setSkillToLearn(skillToLearn);

        Skill skillToTeach = skillService.findById(dto.getSkillToTeachId());
        user.setSkillToTeach(skillToTeach);

        user = userService.save(user);

        Provider newProvider = new Provider();
        newProvider.setName("google");
        newProvider.setProviderUserId(userInfo.getSub());
        newProvider.setUser(user);

        return providerService.save(newProvider).getUser();

    }

    private GoogleUserInfo verifyAndParseIdToken(String idTokenString){
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new IllegalArgumentException("Invalid ID token.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            return toUserInfo(payload);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error verifying ID token", e);
        }
    }

    private GoogleTokenResponse exchangeCode(String code, String redirectUri) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", redirectUri);
        form.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                request,
                GoogleTokenResponse.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Unexpected response status: " + response.getStatusCode());
        }
    }

    private GoogleUserInfo toUserInfo(GoogleIdToken.Payload payload) {
        GoogleUserInfo userInfo = new GoogleUserInfo();
        userInfo.setEmail(payload.getEmail());
        userInfo.setSub(payload.getSubject());

        return userInfo;
    }
}
