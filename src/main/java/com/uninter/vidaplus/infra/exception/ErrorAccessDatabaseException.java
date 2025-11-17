package com.uninter.vidaplus.infra.exception;

import org.springframework.http.HttpStatus;

public class ErrorAccessDatabaseException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "Erro ao acessar base de dados";

    public ErrorAccessDatabaseException() {
        super(STATUS, MESSAGE);
    }
}
