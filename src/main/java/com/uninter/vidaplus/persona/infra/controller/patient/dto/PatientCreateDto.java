package com.uninter.vidaplus.persona.infra.controller.patient.dto;

import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;

public record PatientCreateDto(String name, String lastName, String email, SexEnum sex, Long healthcareFacilityId, String password) {
}
