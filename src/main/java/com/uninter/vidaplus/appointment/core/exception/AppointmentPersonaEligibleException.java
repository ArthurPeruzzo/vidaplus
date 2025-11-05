package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentPersonaEligibleException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public AppointmentPersonaEligibleException(String message) {
        super(STATUS, message);
    }
}
