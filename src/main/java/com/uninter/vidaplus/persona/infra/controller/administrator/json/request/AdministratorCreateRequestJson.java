package com.uninter.vidaplus.persona.infra.controller.administrator.json.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "AdministratorCreateRequestJson",
        description = "Dados necessários para criar um administrador"
)
public record AdministratorCreateRequestJson(
        @Schema(
                description = "Nome do administrador",
                example = "João"
        )
        @NotBlank(message = "O nome deve ser preenchido")
        String name,

        @Schema(
                description = "Sobrenome do administrador",
                example = "Silva"
        )
        @NotBlank(message = "O sobrenome deve ser preenchido")
        String lastName,

        @Schema(
                description = "Email do administrador",
                example = "exemplo@exemplo.com"
        )
        @NotBlank(message = "O email deve ser preenchido")
        String email,

        @Schema(
                description = "Deve conter letras maiúsculas, minúsculas, números e caracteres especiais",
                example = "VidaPlus2025!@#"
        )
        @NotBlank(message = "A senha deve ser preenchida")
        String password) {
}
