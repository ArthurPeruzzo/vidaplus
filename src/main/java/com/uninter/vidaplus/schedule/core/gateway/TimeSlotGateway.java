package com.uninter.vidaplus.schedule.core.gateway;

import com.uninter.vidaplus.schedule.core.domain.TimeSlot;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;

import java.util.List;

public interface TimeSlotGateway {

    void createAllSchedule(List<HealthcareProfessionalSchedule> schedules);
    List<TimeSlot> findAllTimeSlots();
}
