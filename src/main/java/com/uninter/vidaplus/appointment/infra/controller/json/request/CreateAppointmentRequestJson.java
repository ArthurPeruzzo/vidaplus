package com.uninter.vidaplus.appointment.infra.controller.json.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentRequestJson(
        @NotNull(message = "Informe a data da consulta")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime date,
        @NotNull(message = "Informe o profissional de saúde")
        Long healthcareProfessionalId,
        @NotNull(message = "Informe o tipo do atendimento (IN_PERSON, TELECONSULTATION, HOME_VISIT)")
        AppointmentType type) {
}
