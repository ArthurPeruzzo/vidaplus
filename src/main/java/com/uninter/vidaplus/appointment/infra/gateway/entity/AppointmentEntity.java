package com.uninter.vidaplus.appointment.infra.gateway.entity;

import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_professional_id", referencedColumnName = "id")
    private HealthcareProfessionalEntity healthcareProfessional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patient;

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareFacility).map(HealthcareFacilityEntity::getId).orElse(null);
    }

    public String getHealthcareFacilityName() {
        return Optional.ofNullable(healthcareFacility).map(HealthcareFacilityEntity::getName).orElse(null);
    }

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessional).map(HealthcareProfessionalEntity::getId).orElse(null);
    }

    public Long getHealthcareProfessionalUserId() {
        return Optional.ofNullable(healthcareProfessional).map(HealthcareProfessionalEntity::getUserId).orElse(null);
    }

    public String getHealthcareProfessionalName() {
        return Optional.ofNullable(healthcareProfessional).map(HealthcareProfessionalEntity::getName).orElse(null);
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
