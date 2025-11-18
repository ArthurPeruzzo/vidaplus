package com.uninter.vidaplus.appointment.infra.controller.json.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CancelAppointmentRequestJson",
        description = "Dados necessários para cancelar a consulta"
)
public record CancelAppointmentRequestJson(
        @Schema(
                description = "Id da consulta",
                example = "1"
        )
        @NotNull(message = "Informe o id consulta")
        Long appointmentId) {
}
