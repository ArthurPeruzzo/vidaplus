package com.uninter.vidaplus.appointment.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentHealthcareProfessionalScheduleViolationException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public AppointmentHealthcareProfessionalScheduleViolationException(String message) {
        super(STATUS, message);
    }
}
