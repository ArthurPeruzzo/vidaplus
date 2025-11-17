package com.uninter.vidaplus.schedule.infra.gateway.entity;

import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "healthcare_professional_schedule")
public class HealthcareProfessionalScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_professional_id", referencedColumnName = "id")
    private HealthcareProfessionalEntity healthcareProfessional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
    private TimeSlotEntity timeSlot;

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessional)
                .map(HealthcareProfessionalEntity::getId)
                .orElse(null);
    }

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareFacility)
                .map(HealthcareFacilityEntity::getId)
                .orElse(null);
    }

    public Long getTimeSlotId() {
        return Optional.ofNullable(timeSlot)
                .map(TimeSlotEntity::getId)
                .orElse(null);
    }

    public DayOfWeek getTimeSlotDayOfWeek() {
        return Optional.ofNullable(timeSlot)
                .map(TimeSlotEntity::getDayOfWeek)
                .orElse(null);
    }

    public LocalTime getTimeSlotStartTime() {
        return Optional.ofNullable(timeSlot)
                .map(TimeSlotEntity::getStartTime)
                .orElse(null);
    }

    public LocalTime getTimeSlotEndTime() {
        return Optional.ofNullable(timeSlot)
                .map(TimeSlotEntity::getEndTime)
                .orElse(null);
    }

}
