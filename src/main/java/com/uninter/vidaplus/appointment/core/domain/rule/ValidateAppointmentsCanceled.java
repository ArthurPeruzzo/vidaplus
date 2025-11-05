package com.uninter.vidaplus.appointment.core.domain.rule;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.domain.AppointmentStatus;
import com.uninter.vidaplus.appointment.core.domain.rule.dto.InputValidateAppointmentCanceled;
import com.uninter.vidaplus.appointment.core.exception.AppointmentAlreadyCanceledException;
import com.uninter.vidaplus.appointment.core.exception.AppointmentDateViolationException;
import com.uninter.vidaplus.appointment.core.exception.AppointmentPersonaEligibleException;
import com.uninter.vidaplus.infra.rule.BaseRule;
import com.uninter.vidaplus.infra.rule.dto.InputBaseDto;
import com.uninter.vidaplus.infra.rule.dto.OutputBaseDto;
import com.uninter.vidaplus.infra.rule.dto.VoidOutputDto;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateAppointmentsCanceled extends BaseRule {

    private final TokenGateway tokenGateway;

    @Override
    public OutputBaseDto execute(InputBaseDto inputBaseDto) {
        if(!(inputBaseDto instanceof InputValidateAppointmentCanceled input)) {
            return new VoidOutputDto();
        }

        Appointment appointment = input.getAppointment();
        validateStatusAppointment(appointment);
        validateDate(appointment);
        validatePersona(appointment);

        return new VoidOutputDto();
    }

    private void validateStatusAppointment(Appointment appointment) {
        AppointmentStatus status = appointment.getStatus();
        if (AppointmentStatus.CANCELED.equals(status)) {
            log.warn("A consulta já está cancelada");
            throw new AppointmentAlreadyCanceledException();
        }
    }

    private static void validateDate(Appointment appointment) {
        LocalDateTime date = appointment.getDate();
        if (LocalDateTime.now().isAfter(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            log.warn("Não é possível cancelar a consulta de id {}, pois a data da consulta já expirou", appointment.getId());
            throw new AppointmentDateViolationException("Não é possível cancelar a consulta pois a data já expirou - " + date.format(formatter));
        }
    }

    private void validatePersona(Appointment appointment) {
        List<RoleEnum> roles = tokenGateway.getRoles();
        Long userId = tokenGateway.getUserId();

        if (roles.contains(RoleEnum.ROLE_ADMINISTRATOR)) {
            log.warn("Administrador não tem permissão para cancelar consulta");
            throw new AppointmentPersonaEligibleException("Administrador não tem permissão para cancelar consulta");
        }

        if (roles.contains(RoleEnum.ROLE_PATIENT) && !appointment.getPatient().getUserId().equals(userId)) {
            log.warn("não é possível cancelar a consulta pois o paciente não está relacionado a consulta");
            throw new AppointmentPersonaEligibleException("não é possível cancelar a consulta pois o paciente não está relacionado a consulta");
        }

        if (roles.contains(RoleEnum.ROLE_HEALTHCARE_PROFESSIONAL) && !appointment.getHealthcareProfessional().getUserId().equals(userId)) {
            log.warn("não é possível cancelar a consulta pois o profissional não está relacionado a consulta");
            throw new AppointmentPersonaEligibleException("não é possível cancelar a consulta pois o profissional não está relacionado a consulta");
        }
    }
}
