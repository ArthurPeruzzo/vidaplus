package com.uninter.vidaplus.security.authenticate.usecase;

import com.uninter.vidaplus.security.authenticate.controller.json.request.LoginRequestJson;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.infra.token.dto.TokenParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenGateway tokenGateway;

    public String authenticate(LoginRequestJson loginRequestJson) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestJson.email(), loginRequestJson.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return tokenGateway.generateToken(new TokenParams(loginRequestJson.email()));
    }

}
