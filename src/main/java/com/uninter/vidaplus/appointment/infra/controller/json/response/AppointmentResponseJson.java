package com.uninter.vidaplus.appointment.infra.controller.json.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AppointmentResponseJson {

    private final Long id;
    private final LocalDate appointmentDay;
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime dateCreated;

    private final AppointmentPersonaResponseJson healthcareProfessional;
    private final AppointmentPersonaResponseJson patient;
    private final AppointmentHealthcareFacilityResponseJson healthcareFacility;

    private final AppointmentStatus status;
    private final AppointmentType type;
}

