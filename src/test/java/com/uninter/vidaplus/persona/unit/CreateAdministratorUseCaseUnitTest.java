package com.uninter.vidaplus.persona.unit;

import com.uninter.vidaplus.persona.core.domain.administrator.Administrator;
import com.uninter.vidaplus.persona.core.gateway.administrator.AdministratorGateway;
import com.uninter.vidaplus.persona.core.usecase.administrator.CreateAdministratorUseCase;
import com.uninter.vidaplus.persona.infra.controller.administrator.dto.AdministratorCreateDto;
import com.uninter.vidaplus.security.user.core.domain.Email;
import com.uninter.vidaplus.security.user.core.domain.Role;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import com.uninter.vidaplus.security.user.core.domain.User;
import com.uninter.vidaplus.security.user.core.domain.password.PasswordHash;
import com.uninter.vidaplus.security.user.core.gateway.UserGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CreateAdministratorUseCaseUnitTest {

    @InjectMocks
    private CreateAdministratorUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private AdministratorGateway administratorGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateAdministratorSuccessfully() {
        AdministratorCreateDto dto = new AdministratorCreateDto(
                "any", "one", "any@any.com", "VidaPlus2025!@#"
        );

        Mockito.when(passwordEncoder.encode("VidaPlus2025!@#")).thenReturn("encoded");

        User userSaved = new User(10L, new Email("any@any.com"),
                new PasswordHash("encoded"), List.of(new Role(RoleEnum.ROLE_ADMINISTRATOR)));

        Mockito.when(userGateway.create(Mockito.any(User.class))).thenReturn(userSaved);

        Assertions.assertDoesNotThrow(() -> useCase.create(dto));

        ArgumentCaptor<Administrator> administratorCaptor = ArgumentCaptor.forClass(Administrator.class);
        Mockito.verify(administratorGateway).create(administratorCaptor.capture());

        Administrator captured = administratorCaptor.getValue();

        Assertions.assertEquals(10L, captured.getUserId());
        Assertions.assertEquals("any", captured.getName());
        Assertions.assertEquals("one", captured.getLastName());
        Assertions.assertEquals("any@any.com", captured.getEmail());

        Mockito.verify(userGateway).create(Mockito.any(User.class));
        Mockito.verify(passwordEncoder).encode("VidaPlus2025!@#");
    }

    @Test
    void shouldThrowWhenUserCreationFails() {
        AdministratorCreateDto dto = new AdministratorCreateDto(
                "any", "one", "any@any.com", "VidaPlus2025!@#"
        );

        Mockito.when(passwordEncoder.encode("VidaPlus2025!@#")).thenReturn("encoded");
        Mockito.when(userGateway.create(Mockito.any(User.class)))
                .thenThrow(new RuntimeException("Error"));

        Assertions.assertThrows(RuntimeException.class, () -> useCase.create(dto));

        Mockito.verifyNoInteractions(administratorGateway);
    }
}

