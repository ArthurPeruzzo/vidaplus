package com.uninter.vidaplus.appointment.core.domain.rule.dto;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.infra.rule.dto.InputBaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InputValidateAppointmentCanceled extends InputBaseDto {
    private Appointment appointment;
}
