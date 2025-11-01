package com.uninter.vidaplus.appointment.core.gateway;

import com.uninter.vidaplus.appointment.core.domain.Appointment;

import java.util.Optional;

public interface AppointmentGateway {

    void create(Appointment appointment);

    Optional<Appointment> findById(Long appointmentId);

    void update(Appointment appointment);
}
