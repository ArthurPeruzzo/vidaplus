package com.uninter.vidaplus.appointment.core.domain;

import com.uninter.vidaplus.appointment.core.exception.AppointmentDateViolationException;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
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
        LocalDateTime now = LocalDateTime.now();

        validateDate(date, now);

        this.date = date;
        this.dateCreated = now;
        this.healthcareProfessional = healthcareProfessional;
        this.patient = patient;
        this.healthcareFacility = healthcareFacility;
        this.status = AppointmentStatus.SCHEDULED;
        this.type = type;
    }

    private static void validateDate(LocalDateTime date, LocalDateTime now) {
        if (date.isBefore(now.plusDays(1))) {
            throw new AppointmentDateViolationException("A data do agendamento deve ter pelo menos 1 dia de antecedência");
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new AppointmentDateViolationException("Agendamentos não podem ocorrer em finais de semana");
        }

        int hour = date.getHour();

        if (hour < 8 || hour >= 18) {
            throw new AppointmentDateViolationException("Agendamentos só podem ocorrer em horários entre 08:00 e 18:00");
        }
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
