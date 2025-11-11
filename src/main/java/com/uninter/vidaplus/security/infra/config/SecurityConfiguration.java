package com.uninter.vidaplus.security.infra.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserAuthenticationFilter userAuthenticationFilter;

    protected static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/authenticate/login",
    };

    protected static final String [] ENDPOINTS_ADMIN = {
            "/administrators",
            "/patients",
            "/healthcare-professionals",
            "/healthcare-facilities"
    };

    protected static final String[] ENDPOINTS_PATIENT = {
            "/appointments",
            "/appointments/cancel"
    };

    protected static final String[] ENDPOINTS_HEALTHCARE_PROFESSIONAL = {
            "/appointments/cancel",
            "/time-slots"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                                .requestMatchers(ENDPOINTS_HEALTHCARE_PROFESSIONAL).hasRole("HEALTHCARE_PROFESSIONAL")
                                .requestMatchers(ENDPOINTS_PATIENT).hasRole("PATIENT")
                                .anyRequest().denyAll()
                ).exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Não autenticado\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Acesso negado\"}");
                                })
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

