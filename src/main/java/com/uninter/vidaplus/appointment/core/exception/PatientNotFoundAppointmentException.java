package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PatientNotFoundAppointmentException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Paciente nao encontrado";

    public PatientNotFoundAppointmentException() {
        super(STATUS, MESSAGE);
    }
}
