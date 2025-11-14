package com.uninter.vidaplus.appointment.core.domain;

import com.uninter.vidaplus.appointment.core.exception.AppointmentDateViolationException;
import com.uninter.vidaplus.appointment.core.exception.AppointmentHealthcareProfessionalScheduleViolationException;
import com.uninter.vidaplus.healthcarefacility.core.domain.HealthcareFacility;
import com.uninter.vidaplus.persona.core.domain.healthcareprofessional.HealthcareProfessional;
import com.uninter.vidaplus.persona.core.domain.patient.Patient;
import com.uninter.vidaplus.schedule.core.domain.healthcareprofessional.HealthcareProfessionalSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Appointment {
    private Long id;
    private LocalDate appointmentDay;
    private LocalDateTime dateCreated;
    private Patient patient;
    private AppointmentStatus status;
    private AppointmentType type;
    private HealthcareProfessionalSchedule healthcareProfessionalSchedule;

    public Appointment(LocalDateTime appointmentDay, Patient patient, AppointmentType type) {
        LocalDateTime now = LocalDateTime.now();

        validateDate(appointmentDay, now);

        this.appointmentDay = appointmentDay.toLocalDate();
        this.dateCreated = now;
        this.patient = patient;
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

        int year = date.getYear();

        if (year > 2027) {
            throw new AppointmentDateViolationException("Agendamentos permitidos somente até 2027");
        }

    }

    public Long getHealthcareFacilityId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalSchedule::getHealthcareFacility)
                .map(HealthcareFacility::getId)
                .orElse(null);
    }

    public String getHealthcareFacilityName() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalSchedule::getHealthcareFacility)
                .map(HealthcareFacility::getName)
                .orElse(null);
    }

    public Long getHealthcareProfessionalId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalSchedule::getHealthcareProfessional)
                .map(HealthcareProfessional::getId)
                .orElse(null);
    }

    public Long getHealthcareProfessionalUserId() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalSchedule::getHealthcareProfessional)
                .map(HealthcareProfessional::getUserId)
                .orElse(null);
    }

    public String getHealthcareProfessionalName() {
        return Optional.ofNullable(healthcareProfessionalSchedule)
                .map(HealthcareProfessionalSchedule::getHealthcareProfessional)
                .map(HealthcareProfessional::getName)
                .orElse(null);
    }

    public Long getPatientId() {
        return Optional.ofNullable(patient).map(Patient::getId).orElse(null);
    }

    public String getPatientName() {
        return Optional.ofNullable(patient).map(Patient::getName).orElse(null);
    }

    public void cancelAppointment() {
        this.status = AppointmentStatus.CANCELED;
    }

    public void assignSchedule(HealthcareProfessionalSchedule healthcareProfessionalSchedule) {
        Long patientHealthcareFacilityId = patient.getHealthcareFacilityId();
        Long healthcareFacilityId = healthcareProfessionalSchedule.getHealthcareFacilityId();

        if (!healthcareFacilityId.equals(patientHealthcareFacilityId)){
            throw new AppointmentHealthcareProfessionalScheduleViolationException("As unidades hospitalares do paciente e da agenda do profissional nao sao compativeis");
        }
        this.healthcareProfessionalSchedule = healthcareProfessionalSchedule;
    }
}
