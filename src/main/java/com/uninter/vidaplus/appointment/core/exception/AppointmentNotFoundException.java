package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentNotFoundException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Consulta nao encontrada";

    public AppointmentNotFoundException() {
        super(STATUS, MESSAGE);
    }
}
