package com.uninter.vidaplus.persona.unit;

import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.persona.core.gateway.patient.PatientGateway;
import com.uninter.vidaplus.persona.core.usecase.patient.CreatePatientUseCase;
import com.uninter.vidaplus.persona.infra.controller.patient.dto.PatientCreateDto;
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
class CreatePatientUseCaseUnitTest {

    @InjectMocks
    private CreatePatientUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Mock
    private PatientGateway patientGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreatePatientSuccessfully() {
        PatientCreateDto dto = new PatientCreateDto(
                "any name",
                "any",
                "email@email.com",
                SexEnum.MALE,
                1L,
                "VidaPlus2025!@#"
        );

        Mockito.when(passwordEncoder.encode("VidaPlus2025!@#")).thenReturn("encoded");

        User userSaved = new User(
                20L,
                new Email("any@any.com"),
                new PasswordHash("encoded"),
                List.of(new Role(RoleEnum.ROLE_PATIENT))
        );

        Mockito.when(userGateway.create(Mockito.any(User.class))).thenReturn(userSaved);

        Assertions.assertDoesNotThrow(() -> useCase.create(dto));

        ArgumentCaptor<Patient> captor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientGateway).create(captor.capture());

        Patient captured = captor.getValue();

        Assertions.assertEquals(20L, captured.getUserId());
        Assertions.assertEquals(1L, captured.getHealthcareFacilityId());
        Assertions.assertEquals("any name", captured.getName());
        Assertions.assertEquals("any", captured.getLastName());
        Assertions.assertEquals(SexEnum.MALE, captured.getSex());
        Assertions.assertEquals("email@email.com", captured.getEmail());

        Mockito.verify(userGateway).create(Mockito.any(User.class));
        Mockito.verify(passwordEncoder).encode("VidaPlus2025!@#");
    }

    @Test
    void shouldThrowWhenUserCreationFails() {
        PatientCreateDto dto = new PatientCreateDto(
                "any name",
                "any",
                "email@email.com",
                SexEnum.MALE,
                1L,
                "VidaPlus2025!@#"
        );

        Mockito.when(passwordEncoder.encode("VidaPlus2025!@#")).thenReturn("encoded");
        Mockito.when(userGateway.create(Mockito.any(User.class)))
                .thenThrow(new RuntimeException("Error"));

        Assertions.assertThrows(RuntimeException.class, () -> useCase.create(dto));

        Mockito.verifyNoInteractions(patientGateway);
    }
}
