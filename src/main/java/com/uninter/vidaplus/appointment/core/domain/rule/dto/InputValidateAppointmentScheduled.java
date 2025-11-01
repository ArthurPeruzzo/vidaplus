package com.uninter.vidaplus.appointment.core.domain.rule.dto;

import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.infra.rule.dto.InputBaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InputValidateAppointmentScheduled extends InputBaseDto {
    private CreateAppointmentDTO createAppointmentDTO;
    private Long healthcareProfessionalId;
    private Long patientId;
}
