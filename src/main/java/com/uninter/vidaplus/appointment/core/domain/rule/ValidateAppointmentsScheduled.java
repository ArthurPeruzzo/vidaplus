package com.uninter.vidaplus.appointment.core.domain.rule;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.domain.rule.dto.InputValidateAppointmentScheduled;
import com.uninter.vidaplus.appointment.core.exception.AppointmentDateViolationException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.CreateAppointmentDTO;
import com.uninter.vidaplus.infra.rule.BaseRule;
import com.uninter.vidaplus.infra.rule.dto.InputBaseDto;
import com.uninter.vidaplus.infra.rule.dto.OutputBaseDto;
import com.uninter.vidaplus.infra.rule.dto.VoidOutputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateAppointmentsScheduled extends BaseRule {

    private final AppointmentGateway appointmentGateway;


    @Override
    public OutputBaseDto execute(InputBaseDto inputBaseDto) {
        if(!(inputBaseDto instanceof InputValidateAppointmentScheduled input)) {
            return new VoidOutputDto();
        }

        CreateAppointmentDTO createAppointmentDTO = input.getCreateAppointmentDTO();

        LocalDateTime date = createAppointmentDTO.date();
        LocalDateTime startDate = date.minusHours(1);
        LocalDateTime endDate = date.plusHours(1);

        List<Appointment> appointments = appointmentGateway
                .findByHealthcareProfessionalIdOrPatientIdAndDate(input.getHealthcareProfessionalId(), input.getPatientId(), startDate, endDate);

        if (appointments == null || appointments.isEmpty()) {
            return new VoidOutputDto();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String startDateFormated = startDate.format(formatter);
        String endDateFormated = endDate.format(formatter);

        log.warn("Ja existe consulta marcada no horario requisitado");
        throw new AppointmentDateViolationException("Não foi possível agendar a consulta, pois já existem compromissos marcados entre "
                + startDateFormated + " e " + endDateFormated
                + ". Nesse intervalo, o profissional ou o paciente já possui uma consulta agendada. Escolha outro horário.");

    }
}
