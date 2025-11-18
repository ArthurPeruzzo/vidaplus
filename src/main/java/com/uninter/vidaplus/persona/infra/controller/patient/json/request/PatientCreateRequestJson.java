package com.uninter.vidaplus.persona.infra.controller.patient.json.request;

import com.uninter.vidaplus.persona.core.domain.enums.SexEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientCreateRequestJson {
        @Schema(
                description = "Nome do paciente",
                example = "Leonardo"
        )
        @NotBlank(message = "O nome deve ser preenchido")
        private String name;

        @Schema(
                description = "Sobrenome do paciente",
                example = "Souza"
        )
        @NotBlank(message = "O sobrenome deve ser preenchido")
        private String lastName;

        @Schema(
                description = "Email do paciente",
                example = "exemplo@exemplo.com"
        )
        @NotBlank(message = "O email deve ser preenchido")
        private String email;

        @Schema(
                description = "Sexo do paciente",
                example = "MALE"
        )
        @NotNull(message = "O sexo deve ser informado (FEMALE/MALE)")
        private SexEnum sex;

        @Schema(
                description = "Unidade hospitalar que o paciente será vinculado",
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
