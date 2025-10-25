package com.uninter.vidaplus.security.authenticate.usecase;

import com.uninter.vidaplus.security.authenticate.core.exception.BadCredentialsAuthenticateException;
import com.uninter.vidaplus.security.authenticate.core.usecase.AuthenticateUserUseCase;
import com.uninter.vidaplus.security.authenticate.infra.controller.dto.LoginInputDto;
import com.uninter.vidaplus.security.databuilder.domain.UserDataBuilder;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.infra.token.dto.TokenParams;
import com.uninter.vidaplus.security.infra.userdetails.UserDetailsImpl;
import com.uninter.vidaplus.security.user.core.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseUnitTest {

    @InjectMocks
    private AuthenticateUserUseCase useCase;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenGateway tokenGateway;

    @Test
    void shouldThrowInternalAuthenticationServiceExceptionWhenAuthenticateFails() {
        LoginInputDto loginInputDto = new LoginInputDto("any-email", "any-password");

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(InternalAuthenticationServiceException.class);

        var exception = Assertions.assertThrows(BadCredentialsAuthenticateException.class,
                () -> useCase.authenticate(loginInputDto));

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verifyNoMoreInteractions(authenticationManager);

        Mockito.verifyNoInteractions(tokenGateway);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        Assertions.assertEquals("Usuário ou senha incorretos", exception.getMessage());
    }

    @Test
    void shouldThrowBadCredentialsAuthenticateExceptionWhenAuthenticateFails() {
        LoginInputDto loginInputDto = new LoginInputDto("any-email", "any-password");

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        var exception = Assertions.assertThrows(BadCredentialsAuthenticateException.class,
                () -> useCase.authenticate(loginInputDto));

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verifyNoMoreInteractions(authenticationManager);

        Mockito.verifyNoInteractions(tokenGateway);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        Assertions.assertEquals("Usuário ou senha incorretos", exception.getMessage());
    }

    @Test
    void shouldAuthenticateSuccessFully() {
        LoginInputDto loginInputDto = new LoginInputDto("any-email", "any-password");
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        String token = "any-token";

        User user = new UserDataBuilder().build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationMock);

        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetails);

        Mockito.when(tokenGateway.generateToken(Mockito.any(TokenParams.class))).thenReturn(token);

        String tokenResult = Assertions.assertDoesNotThrow(() -> useCase.authenticate(loginInputDto));

        Assertions.assertEquals(token, tokenResult);

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verifyNoMoreInteractions(authenticationManager);

        Mockito.verify(authenticationMock).getPrincipal();
        Mockito.verify(tokenGateway).generateToken(Mockito.any(TokenParams.class));
    }


}
