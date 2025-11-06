package com.uninter.vidaplus.appointment.infra.controller.json.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;

import java.time.LocalDateTime;

public record AppointmentResponseJson(Long id,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                      LocalDateTime date,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                      LocalDateTime dateCreated,
                                      AppointmentPersonaResponseJson healthcareProfessional,
                                      AppointmentPersonaResponseJson patient,
                                      AppointmentHealthcareFacilityResponseJson healthcareFacility,
                                      AppointmentStatus status,
                                      AppointmentType type) {

}
