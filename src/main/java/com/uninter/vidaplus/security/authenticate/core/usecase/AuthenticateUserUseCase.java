package com.uninter.vidaplus.security.authenticate.core.usecase;

import com.uninter.vidaplus.security.authenticate.core.exception.BadCredentialsAuthenticateException;
import com.uninter.vidaplus.security.authenticate.core.exception.UnexpectedErrorAuthenticateException;
import com.uninter.vidaplus.security.authenticate.infra.controller.dto.LoginInputDto;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.infra.token.dto.TokenParams;
import com.uninter.vidaplus.security.infra.userdetails.UserDetailsImpl;
import com.uninter.vidaplus.security.user.core.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenGateway tokenGateway;

    public String authenticate(LoginInputDto loginInputDto) {
        Authentication authenticate = authentication(loginInputDto);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        User user = userDetails.getUser();

        TokenParams tokenParams = new TokenParams(user.getId(), user.getEmail().value(), user.getRolesFormattedAsString());

        return tokenGateway.generateToken(tokenParams);
    }

    private Authentication authentication(LoginInputDto loginInputDto) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginInputDto.email(), loginInputDto.password());

            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (InternalAuthenticationServiceException e) {
            log.error("Erro interno no processo de autenticacao para o email: {}", loginInputDto.email(), e);
            throw new BadCredentialsAuthenticateException();
        } catch (BadCredentialsException e) {
            log.error("Usuario ou senha informados estao incorretos", e);
            throw new BadCredentialsAuthenticateException();
        } catch (Exception e) {
            log.error("Erro inesperado no processo de autenticacao", e);
            throw new UnexpectedErrorAuthenticateException();
        }
    }

}
