package com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.dto;

import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;

public record HealthcareProfessionalCreateDto(
        String name, String lastName, String email,
        HealthcareProfessionalPosition position, Long healthcareFacilityId, String password) {
}
