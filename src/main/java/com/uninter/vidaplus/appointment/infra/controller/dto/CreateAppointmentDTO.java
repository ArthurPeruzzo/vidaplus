package com.uninter.vidaplus.appointment.infra.controller.dto;

import com.uninter.vidaplus.appointment.core.domain.AppointmentType;

import java.time.LocalDateTime;

public record CreateAppointmentDTO(LocalDateTime date, Long healthcareProfessionalId, AppointmentType type) {
}
