package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.dtos.user.UserDto;
import com.zirom.tutorapi.services.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserDetailsService userDetailsService;

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_EXPIRY_MS}")
    private Long jwtExpiryMs;

    @Override
    @Transactional
    public UserDetails validateToken(String token) {
        String userId = extractId(token);
        return userDetailsService.loadUserByUsername(userId);
    }

    @Override
    public String extractId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String generateToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(userDto.getRole().name()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userDto.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
