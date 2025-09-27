package com.uninter.vidaplus.security.infra.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    @Bean
    public String jwtIssuer() {
        return issuer;
    }
}
