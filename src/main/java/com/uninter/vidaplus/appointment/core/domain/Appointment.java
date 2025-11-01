package com.uninter.vidaplus.appointment.core.domain;

import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Appointment {
    private Long id;
    private LocalDateTime date;
    private LocalDateTime dateCreated;
    private HealthcareProfessional healthcareProfessional;
    private Patient patient;
    private HealthcareFacility healthcareFacility;
    private AppointmentStatus status;
    private AppointmentType type;

    public Appointment(LocalDateTime date, HealthcareProfessional healthcareProfessional, Patient patient, HealthcareFacility healthcareFacility, AppointmentType type) {
        this.date = date; //Validar se a data passada eh futura a data de criacao dateCreated
        this.dateCreated = LocalDateTime.now();
        this.healthcareProfessional = healthcareProfessional;
        this.patient = patient;
        this.healthcareFacility = healthcareFacility;
        this.status = AppointmentStatus.SCHEDULED;
        this.type = type;
    }

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareFacility).map(HealthcareFacility::getId).orElse(null);
    }

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessional).map(HealthcareProfessional::getId).orElse(null);
    }

    public Long getPatientId() {
        return Optional.ofNullable(patient).map(Patient::getId).orElse(null);
    }

    public void cancelAppointment() {
        this.status = AppointmentStatus.CANCELED;
    }
}
