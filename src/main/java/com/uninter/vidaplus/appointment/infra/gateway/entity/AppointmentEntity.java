package com.uninter.vidaplus.appointment.infra.gateway.entity;

import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import com.uninter.vidaplus.healthcarefacility.infra.gateway.entity.HealthcareFacilityEntity;
import com.uninter.vidaplus.persona.infra.gateway.healthcareprofessional.entity.HealthcareProfessionalEntity;
import com.uninter.vidaplus.persona.infra.gateway.patient.entity.PatientEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_facility_id", referencedColumnName = "id")
    private HealthcareFacilityEntity healthcareFacility;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healthcare_professional_id", referencedColumnName = "id")
    private HealthcareProfessionalEntity healthcareProfessional;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private PatientEntity patient;
}
