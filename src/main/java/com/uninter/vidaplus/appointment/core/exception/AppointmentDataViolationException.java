package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentDataViolationException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public AppointmentDataViolationException(String message) {
        super(STATUS, message);
    }
}
