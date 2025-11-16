package com.uninter.vidaplus.healthcarefacility.core.exception;

import com.uninter.vidaplus.infra.exception.BaseException;
import org.springframework.http.HttpStatus;

public class HealthcareFacilityAlreadyCreatedException extends BaseException {
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public HealthcareFacilityAlreadyCreatedException(String message) {
        super(STATUS, message);
    }
}
