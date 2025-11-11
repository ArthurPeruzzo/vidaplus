package com.uninter.vidaplus.schedule.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class HealthcareProfessionalNotFoundException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "profissional da saude nao encontrado";

    public HealthcareProfessionalNotFoundException() {
        super(STATUS, MESSAGE);
    }
}