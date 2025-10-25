package com.uninter.vidaplus.persona.infra.controller.patient.json.request;

import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientCreateRequestJson (
        @NotBlank(message = "O nome deve ser preenchido")
        String name,
        @NotBlank(message = "O sobrenome deve ser preenchido")
        String lastName,
        @NotBlank(message = "O email deve ser preenchido")
        String email,
        @NotNull(message = "O sexo deve ser informado (FEMALE/MALE)")
        SexEnum sex,
        @NotBlank(message = "A senha deve ser preenchida")
        String password){}
