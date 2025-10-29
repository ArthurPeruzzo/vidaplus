package com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.json.request;

import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HealthcareProfessionalCreateRequestJson(
        @NotBlank(message = "O nome deve ser preenchido")
        String name,
        @NotBlank(message = "O sobrenome deve ser preenchido")
        String lastName,
        @NotBlank(message = "O email deve ser preenchido")
        String email,
        @NotNull(message = "A posição do profissional deve ser informada (MEDIC, NURSE, TECHNICAL)")
        HealthcareProfessionalPosition position,
        @NotNull(message = "A unidade hospitalar deve ser informada")
        Long healthcareFacilityId,
        @NotBlank(message = "A senha deve ser preenchida")
        String password){}
