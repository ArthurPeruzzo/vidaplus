package com.uninter.vidaplus.schedule.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class HealthcareProfessionalScheduleAlreadyExistsException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "A agenda completa do profissional ja foi criada";

    public HealthcareProfessionalScheduleAlreadyExistsException() {
        super(STATUS, MESSAGE);
    }
}