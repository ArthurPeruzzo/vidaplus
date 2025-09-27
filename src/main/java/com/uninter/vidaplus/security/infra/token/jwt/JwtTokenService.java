package com.uninter.vidaplus.security.infra.token.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.infra.token.dto.TokenParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService implements TokenGateway {

    private static final String EMAIL = "email";
    private static final String ROLES = "roles";

    private final Algorithm algorithm;
    private final String jwtIssuer;

    @Override
    public String generateToken(TokenParams params) {
        try {
            String userId = String.valueOf(params.userId());
            return JWT.create()
                    .withIssuer(jwtIssuer)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(userId)
                    .withClaim(EMAIL, params.email())
                    .withArrayClaim(ROLES, params.roles().toArray(new String[0]))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            return decodeToken(token).getSubject();
        } catch (JWTVerificationException ex){
            throw new JWTVerificationException("Token inválido ou expirado.", ex);
        }
    }

    public String getEmailFromToken(String token) {
        try {
            return decodeToken(token).getClaim(EMAIL).asString();
        } catch (JWTVerificationException ex) {
            log.warn("Falha ao verificar JWT: {}", ex.getMessage());
            throw new JWTVerificationException("Token inválido ou expirado.", ex);
        }
    }

    public DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            log.warn("Falha ao verificar JWT: {}", ex.getMessage());
            throw new JWTVerificationException("Token inválido ou expirado.", ex);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .plusHours(4)
                .toInstant();
    }

}
