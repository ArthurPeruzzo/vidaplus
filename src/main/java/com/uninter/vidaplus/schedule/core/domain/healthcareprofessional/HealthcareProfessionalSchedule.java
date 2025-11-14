package com.uninter.vidaplus.schedule.core.domain.healthcareprofessional;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthcareProfessionalSchedule {
    private Long id;
    private HealthcareProfessional healthcareProfessional;
    private HealthcareFacility healthcareFacility;
    private TimeSlot timeSlot;

    public HealthcareProfessionalSchedule(HealthcareProfessional healthcareProfessional, HealthcareFacility healthcareFacility) {
        this.healthcareProfessional = healthcareProfessional;
        this.healthcareFacility = healthcareFacility;

    }

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessional).map(HealthcareProfessional::getId).orElse(null);
    }

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareFacility).map(HealthcareFacility::getId).orElse(null);
    }

    public Long getTimeSlotId() {
        return Optional.ofNullable(timeSlot).map(TimeSlot::getId).orElse(null);
    }
}
