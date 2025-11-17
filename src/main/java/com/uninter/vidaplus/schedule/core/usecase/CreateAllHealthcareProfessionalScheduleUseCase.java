package com.uninter.vidaplus.schedule.core.usecase;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.gateway.healthcareprofessional.HealthcareProfessionalGateway;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
import com.uninter.vidaplus.schedule.core.exception.HealthcareProfessionalNotFoundException;
import com.uninter.vidaplus.schedule.core.gateway.TimeSlotGateway;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateAllHealthcareProfessionalScheduleUseCase {

    private final TimeSlotGateway timeSlotGateway;
    private final TokenGateway tokenGateway;
    private final HealthcareProfessionalGateway healthcareProfessionalGateway;

    public void createAllSchedules() {
        Long userId = tokenGateway.getUserId();
        List<TimeSlot> timeSlots = timeSlotGateway.findAllTimeSlots();

        HealthcareProfessional healthcareProfessional = findHealthcareProfessionalByUserId(userId);
        Long healthcareFacilityId = healthcareProfessional.getHealthcareFacilityId();

        List<HealthcareProfessionalSchedule> healthcareProfessionalSchedules = buildHealthcareProfessionalSchedule(timeSlots, healthcareProfessional, healthcareFacilityId);

        timeSlotGateway.createAllSchedule(healthcareProfessionalSchedules);
    }

    private HealthcareProfessional findHealthcareProfessionalByUserId(Long userId) {
        return healthcareProfessionalGateway.findByUserId(userId).orElseThrow(() -> {
            log.warn("Profissional de saude nao encontrado para o userId = {}", userId);
            return new HealthcareProfessionalNotFoundException();
        });
    }

    private List<HealthcareProfessionalSchedule> buildHealthcareProfessionalSchedule(List<TimeSlot> timeSlots,
                                                                                     HealthcareProfessional healthcareProfessional,
                                                                                     Long healthcareFacilityId) {
        List<HealthcareProfessionalSchedule> healthcareProfessionalSchedules = new ArrayList<>();
        timeSlots.forEach(timeSlot -> healthcareProfessionalSchedules.add(new HealthcareProfessionalSchedule(
                null,
                healthcareProfessional,
                new HealthcareFacility(healthcareFacilityId),
                timeSlot))
        );
        return healthcareProfessionalSchedules;
    }
}
