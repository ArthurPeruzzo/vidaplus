package com.uninter.vidaplus.infra.exception.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(HttpStatus status, String timestamp, String message) {
}
