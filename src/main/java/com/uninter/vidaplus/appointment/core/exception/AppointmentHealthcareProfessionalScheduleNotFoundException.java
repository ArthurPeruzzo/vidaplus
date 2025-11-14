package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentHealthcareProfessionalScheduleNotFoundException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Agenda do profissional nao encontrada";

    public AppointmentHealthcareProfessionalScheduleNotFoundException() {
        super(STATUS, MESSAGE);
    }
}
