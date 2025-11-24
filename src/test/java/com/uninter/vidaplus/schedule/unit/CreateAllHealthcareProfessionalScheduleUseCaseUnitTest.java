package com.uninter.vidaplus.schedule.unit;

import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.gateway.healthcareprofessional.HealthcareProfessionalGateway;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.exception.HealthcareProfessionalNotFoundException;
import com.uninter.vidaplus.schedule.core.gateway.TimeSlotGateway;
import com.uninter.vidaplus.schedule.core.usecase.CreateAllHealthcareProfessionalScheduleUseCase;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAllHealthcareProfessionalScheduleUseCaseUnitTest {

    @InjectMocks
    private CreateAllHealthcareProfessionalScheduleUseCase useCase;

    @Mock
    private TimeSlotGateway timeSlotGateway;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private HealthcareProfessionalGateway healthcareProfessionalGateway;

    @Test
    void shouldCreateAllSchedulesSuccessfully() {
        Long userId = 50L;

        when(tokenGateway.getUserId()).thenReturn(userId);

        HealthcareProfessional healthcareProfessional =
                new HealthcareProfessional(1L, userId,
                        99L, "Any", "Any",
                        HealthcareProfessionalPosition.MEDIC, "test@test.com");

        when(healthcareProfessionalGateway.findByUserId(userId))
                .thenReturn(Optional.of(healthcareProfessional));

        List<TimeSlot> timeSlots = List.of(
                new TimeSlot(DayOfWeek.SATURDAY, LocalTime.now(), LocalTime.now()),
                new TimeSlot(DayOfWeek.SATURDAY, LocalTime.now(), LocalTime.now())
        );

        when(timeSlotGateway.findAllTimeSlots()).thenReturn(timeSlots);

        Assertions.assertDoesNotThrow(() -> useCase.createAllSchedules());

        verify(timeSlotGateway).createAllSchedule(Mockito.any());
        verify(tokenGateway).getUserId();
        verify(healthcareProfessionalGateway).findByUserId(userId);
        verify(timeSlotGateway).findAllTimeSlots();
        verify(timeSlotGateway).createAllSchedule(anyList());
    }

    @Test
    void shouldThrowWhenHealthcareProfessionalNotFound() {
        Long userId = 50L;

        when(tokenGateway.getUserId()).thenReturn(userId);
        when(healthcareProfessionalGateway.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(HealthcareProfessionalNotFoundException.class,
                () -> useCase.createAllSchedules());

        verify(tokenGateway).getUserId();
        verify(timeSlotGateway).findAllTimeSlots();
        verifyNoMoreInteractions(timeSlotGateway);
        verify(healthcareProfessionalGateway).findByUserId(userId);

    }
}

