package com.uninter.vidaplus.appointment.infra.controller.json.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uninter.vidaplus.appointment.core.domain.AppointmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(
        name = "CreateAppointmentRequest",
        description = "Dados necessários para criar uma nova consulta."
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateAppointmentRequestJson {
    @Schema(
            description = "Data e hora da consulta no formato dd/MM/yyyy HH:mm",
            example = "18/11/2025 13:00"
    )
    @NotNull(message = "Informe a data da consulta")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Schema(
            description = "Id do profissional de saúde",
            example = "2"
    )
    @NotNull(message = "Informe o profissional de saúde")
    private Long healthcareProfessionalId;

    @Schema(
            description = "O tipo do atendimento IN_PERSON = Pessoalmente, TELECONSULTATION = Teleconsulta, HOME_VISIT = Visita domiciliar",
            example = "IN_PERSON"
    )
    @NotNull(message = "Informe o tipo do atendimento (IN_PERSON, TELECONSULTATION, HOME_VISIT)")
    private AppointmentType type;
}
