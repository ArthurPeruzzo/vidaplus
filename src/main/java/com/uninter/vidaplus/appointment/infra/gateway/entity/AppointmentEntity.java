package com.uninter.vidaplus.appointment.infra.gateway.entity;

import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import com.uninter.vidaplus.schedule.infra.gateway.entity.HealthcareProfessionalScheduleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name="appointment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentType type;

    @Column(name = "appointment_day", nullable = false)
    private LocalDate appointmentDay;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_professional_schedule_id", referencedColumnName = "id")
    private HealthcareProfessionalScheduleEntity healthcareProfessionalSchedule;

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalScheduleEntity::getHealthcareFacility)
                .map(HealthcareFacilityEntity::getId)
                .orElse(null);
    }

    public String getHealthcareFacilityName() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalScheduleEntity::getHealthcareFacility)
                .map(HealthcareFacilityEntity::getName)
                .orElse(null);
    }

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalScheduleEntity::getHealthcareProfessional)
                .map(HealthcareProfessionalEntity::getId)
                .orElse(null);
    }

    public Long getHealthcareProfessionalUserId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalScheduleEntity::getHealthcareProfessional)
                .map(HealthcareProfessionalEntity::getUserId)
                .orElse(null);
    }

    public String getHealthcareProfessionalName() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalScheduleEntity::getHealthcareProfessional)
                .map(HealthcareProfessionalEntity::getName)
                .orElse(null);
    }

    public Long getPatientId() {
        return Optional.ofNullable(patient).map(PatientEntity::getId).orElse(null);
    }

    public Long getPatientUserId() {
        return Optional.ofNullable(patient).map(PatientEntity::getUserId).orElse(null);
    }

    public String getPatientName() {
        return Optional.ofNullable(patient).map(PatientEntity::getName).orElse(null);
    }
}
