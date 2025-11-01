package com.uninter.vidaplus.appointment.core.usecase;

import com.uninter.vidaplus.appointment.core.domain.Appointment;
import com.uninter.vidaplus.appointment.core.exception.AppointmentNotFoundException;
import com.uninter.vidaplus.appointment.core.gateway.AppointmentGateway;
import com.uninter.vidaplus.appointment.infra.controller.dto.CancelAppointmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CancelAppointmentUseCase {

    private final AppointmentGateway appointmentGateway;

    public void cancel(CancelAppointmentDTO cancelAppointmentDTO) {

        Long appointmentId = cancelAppointmentDTO.appointmentId();

        Appointment appointment = findAppointmentById(appointmentId);
        appointment.cancelAppointment();

        appointmentGateway.update(appointment);

    }

    private Appointment findAppointmentById(Long appointmentId) {
        return appointmentGateway.findById(appointmentId).orElseThrow(() -> {
            log.warn("Consulta nao encontrada para o id ={}", appointmentId);
            return new AppointmentNotFoundException();
        });
    }
}
