package com.uninter.vidaplus.appointment.infra.controller.json.request;

import jakarta.validation.constraints.NotNull;

public record CancelAppointmentRequestJson(
        @NotNull(message = "Informe o id consulta")
        Long appointmentId) {
}
