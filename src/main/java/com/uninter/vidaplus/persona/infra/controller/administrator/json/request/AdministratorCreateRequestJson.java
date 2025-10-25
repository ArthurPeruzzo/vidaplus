package com.uninter.vidaplus.persona.infra.controller.administrator.json.request;

import jakarta.validation.constraints.NotBlank;

public record AdministratorCreateRequestJson(
        @NotBlank(message = "O nome deve ser preenchido")
        String name,
        @NotBlank(message = "O sobrenome deve ser preenchido")
        String lastName,
        @NotBlank(message = "O email deve ser preenchido")
        String email,
        @NotBlank(message = "A senha deve ser preenchida")
        String password) {
}
