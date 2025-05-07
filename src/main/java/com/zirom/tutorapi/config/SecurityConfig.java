package com.zirom.tutorapi.config;

import com.zirom.tutorapi.repositories.UserRepository;
import com.zirom.tutorapi.security.ApiUserDetailsService;
import com.zirom.tutorapi.security.JwtAuthenticationFilter;
import com.zirom.tutorapi.services.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(GoogleOAuthService googleOAuthService) {
        return new JwtAuthenticationFilter(googleOAuthService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        ApiUserDetailsService apiUserDetailsService = new ApiUserDetailsService(userRepository);
        return apiUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/google").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/google/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/skill/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/skill").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
