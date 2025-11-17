package com.uninter.vidaplus.schedule.core.gateway;

import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotGateway {

    void createAllSchedule(List<HealthcareProfessionalSchedule> schedules);
    List<TimeSlot> findAllTimeSlots();
    Optional<HealthcareProfessionalSchedule> findScheduleBy(Long healthcareProfessionalId, Long healthcareFacilityId, LocalDateTime time);
}
