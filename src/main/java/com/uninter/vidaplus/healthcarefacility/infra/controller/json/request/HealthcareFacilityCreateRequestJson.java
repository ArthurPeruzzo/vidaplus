package com.uninter.vidaplus.healthcarefacility.infra.controller.json.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Schema(
        name = "HealthcareFacilityCreateRequestJson",
        description = "Dados necessários para criar uma unidade hospitalar"
)
public record HealthcareFacilityCreateRequestJson(
        @Schema(
                description = "Nome da unidade hospitalar",
                example = "Santa Maria"
        )
        @NotBlank(message = "O nome deve ser preenchido")
        String name,

        @Schema(
                description = "CNPJ da unidade hospitalar. Deve ser seguido o seguinte formato de exemplo: 00.000.000/0000-00 ou sem formatação",
                example = "26.793.920/0001-06",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @CNPJ(message = "O conteúdo ou a formatação do cnpj não é válida. Deve ser seguido o seguinte formato de exemplo: 00.000.000/0000-00 ou sem formatação")
        String cnpj) {
}
