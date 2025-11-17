package com.uninter.vidaplus.persona.core.domain.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PersonaAlreadyCreatedException extends BaseException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public PersonaAlreadyCreatedException(String message) {
        super(STATUS, message);
    }
}
