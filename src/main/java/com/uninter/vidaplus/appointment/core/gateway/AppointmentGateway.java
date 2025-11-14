package com.uninter.vidaplus.appointment.core.gateway;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.infra.controller.dto.FilterParamsDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentGateway {
    void create(Appointment appointment);
    Optional<Appointment> findById(Long appointmentId);
    void update(Appointment appointment);
    List<Appointment> findByHealthcareProfessionalIdOrPatientIdAndDate(Long healthcareProfessionalId, Long patientId, LocalTime startDate, LocalTime endDate, LocalDate appointmentDay);
    Page<Appointment> findAllPatientAppointmentByUserId(Long userId, FilterParamsDTO paramsDTO);
}
