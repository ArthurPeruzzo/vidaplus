package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentAlreadyCanceledException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final String MESSAGE = "A consulta já está cancelada";

    public AppointmentAlreadyCanceledException() {
        super(STATUS, MESSAGE);
    }
}
