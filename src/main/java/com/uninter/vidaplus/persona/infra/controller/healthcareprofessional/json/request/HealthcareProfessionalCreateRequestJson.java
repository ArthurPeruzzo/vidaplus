package com.uninter.vidaplus.persona.infra.controller.healthcareprofessional.json.request;

import com.uninter.vidaplus.persona.core.domain.enums.HealthcareProfessionalPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        name = "HealthcareProfessionalCreateRequestJson",
        description = "Dados necessários para criar um profissional da saúde"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HealthcareProfessionalCreateRequestJson {
        @Schema(
                description = "Nome do profissional",
                example = "Pedro"
        )
        @NotBlank(message = "O nome deve ser preenchido")
        private String name;

        @Schema(
                description = "Sobrenome do profissional",
                example = "Linhares"
        )
        @NotBlank(message = "O sobrenome deve ser preenchido")
        private String lastName;

        @Schema(
                description = "Email do profissional",
                example = "exemplo@exemplo.com"
        )
        @NotBlank(message = "O email deve ser preenchido")
        private String email;

        @Schema(
                description = "Posição do profissional",
                example = "MEDIC"
        )
        @NotNull(message = "A posição do profissional deve ser informada (MEDIC, NURSE, TECHNICAL)")
        private HealthcareProfessionalPosition position;

        @Schema(
                description = "Unidade hospitalar que o profissional será vinculado",
                example = "1"
        )
        @NotNull(message = "A unidade hospitalar deve ser informada")
        private Long healthcareFacilityId;

        @Schema(
                description = "A senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais",
                example = "VidaPlus2025!@#"
        )
        @NotBlank(message = "A senha deve ser preenchida")
        private String password;
}
