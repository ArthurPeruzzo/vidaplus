package com.uninter.vidaplus.appointment.core.gateway;

import com.uninter.vidaplus.appointment.core.domain.Appointment;

public interface AppointmentGateway {

    void create(Appointment appointment);
}
