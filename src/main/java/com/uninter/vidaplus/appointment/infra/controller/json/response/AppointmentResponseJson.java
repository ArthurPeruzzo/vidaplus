package com.uninter.vidaplus.appointment.infra.controller.json.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentResponseJson(Long id,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate appointmentDay,
                                      DayOfWeek dayOfWeek,
                                      LocalTime startTime,
                                      LocalTime endTime,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                      LocalDateTime dateCreated,
                                      AppointmentPersonaResponseJson healthcareProfessional,
                                      AppointmentPersonaResponseJson patient,
                                      AppointmentHealthcareFacilityResponseJson healthcareFacility,
                                      AppointmentStatus status,
                                      AppointmentType type) {

}

