package com.uninter.vidaplus.healthcarefacility.infra.controller.json.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record HealthcareFacilityCreateRequestJson(
        @NotBlank(message = "O nome deve ser preenchido")
        String name,
        @CNPJ(message = "O formato do cnpj não é válido. Deve ser seguido o seguinte formato de exemplo: 00.000.000/0000-00 ou sem formatação")
        String cnpj) {
}
