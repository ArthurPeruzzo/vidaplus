package com.uninter.vidaplus.healthcarefacility.infra.controller.json.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthcareFacilityResponseJson {
    private Long id;
    private String name;
    private String cnpj;
}
