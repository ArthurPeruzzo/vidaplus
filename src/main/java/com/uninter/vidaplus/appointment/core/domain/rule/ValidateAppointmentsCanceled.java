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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDate date = appointment.getAppointmentDay();
        LocalTime startTime = appointment.getStartTimeTimeSlot();
        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (today.isAfter(date)) {
            log.warn("Não é possível cancelar a consulta de id {}, pois a data já expirou", appointment.getId());
            throw new AppointmentDateViolationException(
                    "Não é possível cancelar a consulta pois já ocorreu em " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );
        }

        if (today.isEqual(date) && (nowTime.isAfter(startTime) || nowTime.equals(startTime))) {
            String formatted = LocalDateTime.of(date, startTime).format(formatter);
            log.warn("Não é possível cancelar a consulta de id {}, pois já iniciou às {}", appointment.getId(), formatted);
            throw new AppointmentDateViolationException(
                    "Não é possível cancelar a consulta pois já iniciou às " + formatted
            );
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

        if (roles.contains(RoleEnum.ROLE_HEALTHCARE_PROFESSIONAL) && !appointment.getHealthcareProfessionalUserId().equals(userId)) {
            log.warn("não é possível cancelar a consulta pois o profissional não está relacionado a consulta");
            throw new AppointmentPersonaEligibleException("não é possível cancelar a consulta pois o profissional não está relacionado a consulta");
        }
    }
}
