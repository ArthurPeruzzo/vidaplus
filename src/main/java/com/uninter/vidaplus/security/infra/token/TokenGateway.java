package com.uninter.vidaplus.security.infra.token;

import com.uninter.vidaplus.security.infra.token.dto.TokenParams;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;

import java.util.List;

public interface TokenGateway {
    String generateToken(TokenParams params);
    String getEmail();
    Long getUserId();
    List<RoleEnum> getRoles();
}
