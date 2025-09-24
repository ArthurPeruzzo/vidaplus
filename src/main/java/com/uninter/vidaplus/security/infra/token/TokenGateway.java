package com.uninter.vidaplus.security.infra.token;

import com.uninter.vidaplus.security.infra.token.dto.TokenParams;

public interface TokenGateway {
    String generateToken(TokenParams params);
}
