package com.uninter.vidaplus.appointment.core.usecase;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.FilterParamsDTO;
import com.uninter.vidaplus.security.infra.token.TokenGateway;
import com.uninter.vidaplus.security.user.core.domain.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAppointmentsByTokenUseCase {

    private final TokenGateway tokenGateway;
    private final AppointmentGateway appointmentGateway;

    public Page<Appointment> get(FilterParamsDTO params) {
        List<RoleEnum> roles = tokenGateway.getRoles();
        Long userId = tokenGateway.getUserId();

        if (roles.contains(RoleEnum.ROLE_PATIENT)) {
            return appointmentGateway.findAllPatientAppointmentByUserId(userId, params);
        }

        return appointmentGateway.findAllHealthcareProfessionalAppointmentByUserId(userId, params);
    }
}
